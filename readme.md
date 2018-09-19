## Параллельные вычисления с помощью Scala

В текущем мире данные растут постепенно, при этом необходимо решить более сложные вычислительные задачи. Что нам делать 
с решением проблем, которые не подходят ни одному из основных процессоров или не могут быть решены в разумные сроки?

Параллельное программирование не только решает эти проблемы, но и сокращает время вычисления. В наши дни все компьютеры 
являются `многоядерными процессорами`, даже у вашего iPhone 10 есть 6 ядер. В этой статье я рассмотрю возможные сценарии,
 в которых мы можем применить параллельные вычисления и факторы, влияющие на время вычислений. Мы также рассмотрим 
 сравнительный анализ параллельных программ.

![alt text](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/images/parallel_computing.jpg "parallel_computing")

**Параллельные вычисления** - это тип вычислений, в котором различные вычисления могут выполняться одновременно.

_Основной принцип_: проблему можно разделить на подзадачи, каждая из которых может быть решена одновременно.

### Содержание:

* [0 - Эффект задержки (вступление)](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/readmes/theory_1.md)

* [1 - Параллельность уровня задачи](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/readmes/task_level_parallelism.md)

* [Монада Future[T]](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/readmes/Future.md)

* [Наблюдатель (Observable)](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/readmes/Observable.md)

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


[=>далее: Эффект задержки (Latency as an effect)](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/readmes/theory_1.md)


_Если этот проект окажется полезным тебе - нажми на кнопочку **`★`** в правом верхнем углу._
