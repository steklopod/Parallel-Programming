## Обещания (Promises)

До сих пор мы рассматривали только объекты `Future`, созданные асинхронными вычислениями, которые начали использовать 
метод `future`. Однако фьючерсы также могут быть созданы с использованием обещаний.

В то время как фьючерсы определяются как тип объекта-заполнителя только для чтения, созданного для результата, которого 
еще нет, обещание можно рассматривать как записываемый контейнер с одним присваиванием, который завершает будущее. 
То есть, обещание может быть использовано для успешного завершения будущего со значением (путем «завершения» обещания) с 
использованием метода `success`. И наоборот, обещание можно также использовать для завершения будущего с исключением, не 
выполнив обещание, используя метод `failure`.

Обещание `p` завершает будущее, возвращенное `p.future`. Это будущее относится к обещанию `p`. В зависимости от реализации 
это может быть случай, когда `p.future eq p`.

Рассмотрим следующий пример производителя-потребителя, в котором одно вычисление дает значение и передает его другому 
вычислению, которое потребляет это значение. Эта передача значения выполняется с использованием обещания.

```scala
    import scala.concurrent.{ Future, Promise }
    import scala.concurrent.ExecutionContext.Implicits.global
    
    val p = Promise[T]()
    val f = p.future
    
    val producer = Future {
      val r = produceSomething()
      p success r
      continueDoingSomethingUnrelated()
    }
    
    val consumer = Future {
      startDoingSomething()
      f foreach { r =>
        doSomethingWithResult()
      }
    }
```

Здесь мы создаем обещание и используем его метод `future` для получения `Future`, который он завершает. Затем мы начинаем 
два асинхронных вычисления. _Первый_ выполняет некоторые вычисления, в результате чего значение `r`, которое затем используется 
для завершения будущего `f`, выполняя обещание `p`. _Второй_ выполняет некоторые вычисления, а затем считывает результат 
`r` завершенного будущего `f`. Обратите внимание, что потребитель (`consumer`) может получить результат до завершения 
задачи производителя (`producer`), выполнив метод `continueDoingSomethingUnrelated()`.

Как упоминалось ранее, обещания имеют семантику с одним присваиванием. Таким образом, они могут быть завершены только 
один раз. Вызов успеха по обещанию, которое уже было завершено (или не выполнено), вызовет исключение `IllegalStateException`.

В следующем примере показано, как выполнить обещание.

```scala
    val p = Promise[T]()
    val f = p.future
    
    val producer = Future {
      val r = someComputation
      if (isInvalid(r))
        p failure (new IllegalStateException)
      else {
        val q = doSomeMoreComputation(r)
        p success q
      }
    }
```

Здесь производитель (`producer`) вычисляет промежуточный результат `r` и проверяет, действительно ли он. В случае, если 
он недействителен, он не выполняет обещание, выполнив обещание `p` с исключением. В этом случае связанное будущее `f` не 
выполняется. В противном случае продюсер продолжит вычисление и, наконец, завершит будущее `f` действительным результатом, 
выполнив обещание `p`.

Обещания также могут быть завершены с методом `complete`, который принимает потенциальное значение `Try[T]` - либо неудачный 
результат типа `Failure[Throwable]`, либо успешный результат типа `Success[T]`.

Аналогично успеху (`success`), вызвав неудачу (`failure`) и завершив (`complete`) обещание, которое уже было завершено, 
выйдет исключение `IllegalStateException`.

Одно приятное свойство программ, написанных с использованием обещаний с описанными до сих пор операциями и фьючерсами, 
которые состоят из монадических операций без побочных эффектов, заключается в том, что эти **программы детерминированы**. 
Детерминированный здесь означает, что, учитывая, что в программе не выбрано исключение, результат программы (значения, 
наблюдаемые в фьючерсах) всегда будут одинаковыми независимо от расписания выполнения параллельной программы.

В некоторых случаях клиент может захотеть выполнить обещание только в том случае, если он еще не завершен (например, есть 
несколько HTTP-запросов, выполняемых из нескольких разных фьючерсов, и клиент заинтересован только в первом ответе HTTP - 
в соответствии с первым будущим для выполнения обещания). По этим причинам методы `tryComplete`, `trySuccess` и `tryFailure`
 существуют в будущем. Клиент должен знать, что использование этих методов приводит к программам, которые не являются 
 детерминированными, но зависят от графика выполнения.

Метод `completeWith` завершает обещание с другим будущим. После того, как будущее будет завершено, обещание завершится и 
результатом этого будущего. Следующая программа печатает `1`:

```scala
    val f = Future { 1 }
    val p = Promise[Int]()
    
    p completeWith f
    
    p.future foreach { x =>
      println(x)
    }
```

При отсутствии обещания с исключением, три подтипа `Throwables` обрабатываются специально. Если `Throwable` используется 
для нарушения обещания - это `scala.runtime.NonLocalReturnControl`, то обещание завершается с соответствующим значением. 
Если `Throwable`, используемый для нарушения обещания, является экземпляром `Error`, `InterruptedException` или 
`scala.util.control.ControlThrowable`, `Throwable` завершается как причина нового `ExecutionException`, которое, в свою 
очередь, не дает обещания.

Используя обещания, метод `onComplete` фьючерсов и будущую конструкцию вы можете реализовать любой из комбинаторов 
функциональных компонов, описанных ранее. Предположим, вы хотите сначала внедрить новый комбинатор, который принимает два 
фьючерса `f` и `g` и создает третье будущее, которое завершается либо `f`, либо `g` (в зависимости от того, что наступит раньше), 
а только при условии, что оно выполнено успешно.

Вот пример того, как это сделать:

```scala
    def first[T](f: Future[T], g: Future[T]): Future[T] = {
      val p = promise[T]
    
      f foreach { x =>
        p.trySuccess(x)
      }
    
      g foreach { x =>
        p.trySuccess(x)
      }
    
      p.future
    }
```

Обратите внимание, что в этой реализации, если ни `f`, ни `g` не удается, `first(f, g)` никогда не завершается 
(либо со значением, либо с исключением).

_Если этот проект окажется полезным тебе - нажми на кнопочку **`★`** в правом верхнем углу._

[<= содержание](https://github.com/steklopod/Parallel-Programming/blob/master/readme.md)