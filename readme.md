## Параллельные вычисления с помощью Scala

В текущее время объем данных непрерывно растет, при этом необходимо решать более сложные вычислительные задачи. Что нам делать 
с решением проблем, которые  не могут быть решены в разумные сроки?

![alt text](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/images/parallel_computing.jpg "parallel_computing")

**Параллельные вычисления** - это тип вычислений, в котором различные вычисления могут выполняться одновременно.

_Основной принцип_: проблему можно разделить на подзадачи, каждая из которых может быть решена одновременно.

* [Вступление](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/readmes/theory_1.md)

* [1 - Контекст выполнения (Execution Context)](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/readmes/ExecutionContext.md)

* [2 - Монада  Будущее (Future[T])](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/readmes/Future.md)

* [2.1 - Обратные вызовы (Callbacks)](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/readmes/Callbacks.md)

* [2.1 - Композиция функций и for-comprehensions](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/readmes/Functional_Composition.md)

* [2.2 - Проекции](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/readmes/Projections.md)

* [3 - Блокировка](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/readmes/Blocking.md)

* [4 - Исключения (Exceptions)](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/readmes/Exceptions.md)

* [5 - Обещания (Promises)](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/readmes/Promises.md)

* [6 - Утилиты (Utilities)](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/readmes/Utilities.md)

___

Многие из нас не видят разницу между параллельным (parallel) и действующий одновременно (concurrent) программированием. 
Давайте сделаем шаг назад и поймем концепцию и рассуждения о параллельных вычислениях.

**Параллельное (parallel) вычисление:**

* Оптимальное использование параллельного оборудования для быстрого выполнения вычислений;

* Разделение на подзадачи;

* В основном забота о: _скорость_;

* В основном используется для: Алгоритмических задач, числовых вычислений, обработки больших данных.

**Конкурентное (concurrent) программирование:**

* Может или не может предлагать несколько запусков запуска одновременно;

* В основном забота о: _удобстве, лучшей отзывчивости, ремонтопригодности_.

Существуют различные уровни параллелизма, например: _разрядный уровень, уровень команд и уровень задач_. Для целей этой 
статьи мы сосредоточимся на **параллелизме уровня задач**.

Параллельное программирование намного сложнее, чем последовательное программирование. Это даже делает жизнь разработчиков 
сложнее. Тем не менее, скорость, с которой могут быть достигнуты результаты, является большим плюсом на стороне параллельного 
программирования.

Операционная система и JVM являются основными средами, которые делают возможными параллелизм. Как известно, два разных 
процесса не разделяют память, которая служит в качестве блокатора для параллельной работы задачи. Но каждый процесс 
содержит несколько независимых параллельных единиц, называемых потоками. Потоки имеют одинаковое адресное пространство.


[=>далее: Эффект задержки (Latency as an effect)](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/readmes/theory_1.md)


_Если этот проект окажется полезным тебе - нажми на кнопочку **`★`** в правом верхнем углу._
