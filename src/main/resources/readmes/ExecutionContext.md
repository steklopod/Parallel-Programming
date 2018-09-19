### Введение

Фьючерсы дают возможность рассуждать о выполнении многих операций параллельно - эффективным и неблокирующим способом. 
[Future](https://www.scala-lang.org/api/current/scala/concurrent/Future.html) является объектом-заполнителем (монадой) для значения, 
которое может еще не существовать. Составление параллельных задач приводит к более быстрому, асинхронному, неблокирующему 
параллельному коду.

По умолчанию фьючерсы и обещания (`promise`s) не блокируются, используя обратные вызовы вместо типичных операций блокировки. 
Чтобы упростить использование обратных вызовов как синтаксически, так и концептуально, Scala предоставляет комбинаторы, 
такие как `flatMap`, `foreach` и `filter`, используемые для создания фьючерсов неблокирующим способом. Блокировка 
по-прежнему возможна - для случаев, когда это абсолютно необходимо, фьючерсы могут быть заблокированы (хотя это не рекомендуется).

Типичный `Future` выглядит так:

```scala
    val inverseFuture: Future[Matrix] = Future {
      fatMatrix.inverse() // неблокирующее долговременное вычисление
    }(executionContext)
```

Или более идиоматически:

```scala
    implicit val ec: ExecutionContext = ...
    val inverseFuture : Future[Matrix] = Future {
      fatMatrix.inverse()
    } // ec неявно передается
```

Оба фрагмента кода делегируют выполнение `fatMatrix.inverse()` в `ExecutionContext` и воплощают результат вычисления в `inverseFuture`.

### Контекст выполнения (Execution Context)

Будущее (Future) и Обещания (Promises) вращаются вокруг `ExecutionContexts`, ответственных за выполнение вычислений.

`ExecutionContext` похож на [Executor](http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/Executor.html): 
он может выполнять вычисления в новом потоке, в объединенном потоке или в текущем потоке (хотя выполнение вычисления в 
текущем потоке не рекомендуется - подробнее об этом ниже).

Пакет `scala.concurrent` поставляется из коробки с реализацией `ExecutionContext`, глобальным статическим пулом потоков. 
Также можно преобразовать `Executor` в `ExecutionContext`. Наконец, пользователи могут расширять типаж `ExecutionContext`, 
чтобы реализовать свои собственные контексты выполнения, хотя это нужно делать только в редких случаях.

### Глобальный контекст выполнения (The global execution context)

`ExecutionContext.global` - это `ExecutionContext`, поддерживаемый `ForkJoinPool`. Этого должно быть достаточно для 
большинства ситуаций, но требует некоторой осторожности. A [ForkJoinPool](http://docs.oracle.com/javase/tutorial/essential/concurrency/forkjoin.html)
 управляет ограниченным количеством потоков 
(максимальное количество потоков относится к уровню параллелизма). Количество одновременных блокирующих вычислений может 
превышать уровень параллелизма только в том случае, если каждый блокирующий вызов обернут внутри блокирующего вызова 
(подробнее об этом ниже). В противном случае существует риск того, что пул потоков в контексте глобального исполнения 
будет голоден, и никакие вычисления не могут продолжаться.

По умолчанию `ExecutionContext.global` устанавливает уровень параллелизма своего базового fork-join-pool'а в число 
доступных процессоров ([Runtime.availableProcessors](http://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html#availableProcessors%28%29)).
 Эта конфигурация может быть переопределена путем установки одного (или нескольких) следующих атрибутов виртуальной машины:
 
* scala.concurrent.context.minThreads - по умолчанию используется `Runtime.availableProcessors`
 
* scala.concurrent.context.numThreads - может быть числом или множителем (N) в форме «xN»; по умолчанию - `Runtime.availableProcessors`
 
* scala.concurrent.context.maxThreads - по умолчанию для `Runtime.availableProcessors`

Уровень параллелизма будет установлен на `numThreads`, если он остается внутри `[minThreads; MaxThreads]`.

Как указано выше, `ForkJoinPool` может увеличивать количество потоков за пределами его `parallelismLevel` при наличии 
блокировки вычислений. Как объясняется в API ForkJoinPool, это возможно только в том случае, если пул явно уведомлен:

![alt text](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/images/future-thread.jpg "Future 2")

```scala
    import scala.concurrent.Future
    import scala.concurrent.forkjoin._
    
    // следующее эквивалентно `implicit val ec = ExecutionContext.global`
    import ExecutionContext.Implicits.global
    
    Future {
      ForkJoinPool.managedBlock(
        new ManagedBlocker {
           var done = false
    
           def block(): Boolean = {
             try {
               myLock.lock()
               // ...
             } finally {
              done = true
             }
             true
           }
    
           def isReleasable: Boolean = done
        }
      )
    }
```

К счастью, параллельный пакет обеспечивает удобный способ для этого:

```scala
    import scala.concurrent.Future
    import scala.concurrent.blocking
    
    Future {
      blocking {
        myLock.lock()
        // ...
      }
    }
```

Обратите внимание, что блокировка является общей конструкцией, которая будет более подробно рассмотрена ниже.

И последнее, но не менее важное: помните, что ForkJoinPool не предназначен для длительных операций блокировки. Даже 
если уведомление с блокировкой пула не может порождать новых работников, как вы ожидали бы, и когда новые рабочие 
создаются, их может быть целых 32767. Чтобы дать вам представление, следующий код будет использовать 32000 потоков:


```scala
    implicit val ec = ExecutionContext.global
    
    for( i <- 1 to 32000 ) {
      Future {
        blocking {
          Thread.sleep(999999)
        }
      }
    }
```

Если вам нужно завернуть длительные операции блокировки, мы рекомендуем использовать выделенный `ExecutionContext`, 
например, путем переноса `Executor` из Java. 


_Если этот проект окажется полезным тебе - нажми на кнопочку **`★`** в правом верхнем углу._

[<= содержание](https://github.com/steklopod/Parallel-Programming/blob/master/readme.md)