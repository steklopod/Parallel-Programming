_Прежде чем приступать к проблемам параллельного программирования, давайте сначала поймем, как работает аппаратное обеспечение.
Производители процессоров, такие как IBM и Oracle, обеспечивают многоядерный процессор на одном чипе процессора, каждый 
из которых способен выполнять отдельный поток команд:_

* Многоядерный процессор;

* Симметричные мультипроцессоры;

* Блок графической обработки.

![alt text](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/images/1_task_level_parallelism/cpu.png "Производители процессоров")

### Параллельность уровня задачи

Задача может быть заявлена как параллельное выполнение отдельных потоков команд.

![alt text](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/images/1_task_level_parallelism/task_level_parallelism.png)

Сигнатура параллельного выполнения, где `taskA` и `taskB` вызывается **по имени** (call by name), означает, что она 
**выполняется параллельно**.

![alt text](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/images/1_task_level_parallelism/byname.png)

___

_Давайте посмотрим на пример:_

Найдем общее количество способов размена для указанного списка монет за указанную сумму денег.
Этот метод `countChange` выполняется последовательно и дает результат за **82.6 мс**:

![alt text](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/images/1_task_level_parallelism/countChange.png)

В то время как параллельная версия работает в **48.7 мс** с ускорением в **1,7 раза**.

![alt text](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/images/1_task_level_parallelism/countChangepar.png)

> Вычислялось на MacBook Pro «Core i5», 2,4 ГГц (двух независимых процессорных «ядер» на одном кремниевом чипе), память 8 ГБ.

Давайте попробуем понять стоимость разделения и объединения структур данных, таких как массивы.

Предположим, у нас есть 4-ехъядерный процессор и размер массива 100, над которым мы должны выполнить операцию фильтрации.

![alt text](https://github.com/steklopod/Parallel-Programming/blob/master/src/main/resources/images/1_task_level_parallelism/4core.png)

На уровне листа дерева параллельной редукции он будет пересекать `N` элементов в `N/4` вычислительных шагах и выполнять 
фильтрацию. Процесс `P0` и `P1` создает два массива и аналогично `P2` и `P3`.

На следующем шаге два массива будут объединены вместе в `N/2` вычислительных шагах. 
Наконец, на корневом уровне нам нужно объединить два массива, которые занимают N вычислительных шагов.

Суммируя все вычислительные этапы, мы обнаружили, что `7N/4> N > N/4`.

Общая сложность `O(n+m)`, что неэффективно. Кроме того, мы видим, что объединение занимает столько же времени, сколько и 
фильтрация. 


_Если этот проект окажется полезным тебе - нажми на кнопочку **`★`** в правом верхнем углу._

[<= содержание](https://github.com/steklopod/Parallel-Programming/blob/master/readme.md)

