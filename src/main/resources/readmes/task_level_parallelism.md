_Прежде чем приступать к проблемам параллельного программирования, давайте сначала поймем, как работает аппаратное обеспечение.
Производители процессоров, такие как IBM и Oracle, обеспечивают многоядерный процессор на одном чипе процессора, каждый 
из которых способен выполнять отдельный поток команд:_

* Многоядерный процессор;

* Симметричные мультипроцессоры;

* Блок графической обработки.

![alt text](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/images/1_task_level_parallelism/cpu.png "Производители процессоров")

### Параллельность уровня задачи

Задача может быть заявлена как параллельное выполнение отдельных потоков команд.

![alt text](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/images/1_task_level_parallelism/task_level_parallelism.png)

Сигнатура параллельного выполнения, где `taskA` и `taskB` вызывается **по имени** (call by name), означает, что она 
**выполняется параллельно**.
![alt text](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/images/1_task_level_parallelism/byname.png)

_Давайте посмотрим на пример:_

Найдем общее количество способов размена для указанного списка монет за указанную сумму денег.
![alt text](https://github.com/steklopod/Timely-Effects/blob/master/src/main/resources/images/1_task_level_parallelism/countChange.png)


_Если этот проект окажется полезным тебе - нажми на кнопочку **`★`** в правом верхнем углу._

[<= содержание](https://github.com/steklopod/Timely-Effects/blob/master/readme.md)

