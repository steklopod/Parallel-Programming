## Утилиты (Utilities)

Для упрощения обработки времени в параллельных приложениях `scala.concurrent` вводит абстракцию длительности (`Duration`).
 Продолжительность не должна быть еще одной общей абстракцией времени. Он предназначен для использования с библиотеками
  параллелизма и находится в пакете `scala.concurrent`.

**Длительность (`Duration`)** - это базовый класс, представляющий длину времени. Он может быть либо конечным, либо бесконечным.
 Конечная продолжительность представлена классом `FiniteDuration`, который построен из длинны `Long` и `java.util.concurrent.TimeUnit`.
  Бесконечные длительности, также расширенные от `Duration`, существуют только в двух экземплярах: `Duration.Inf` и 
  `Duration.MinusInf`. Библиотека также предоставляет несколько подклассов `Duration` для целей неявного преобразования, 
  и они не должны использоваться.

Абстактный `Duration` содержит методы, которые позволяют:

1. Преобразование в разные единицы времени (`toNanos`, `toMicros`, `toMillis`, `toSeconds`, `toMinutes`, `toHours`, 
`toDays` и `toUnit` (единица: `TimeUnit`));

2. Сравнение длительностей (`<`, `<=`, `>` и `>=`);

3. Арифметические операции (+, -, *, / и unary_-);

4. Минимальная и максимальная между этой длительностью и величиной, указанной в аргументе (`min`, `max`);

5. Проверьте, является ли продолжительность конечной (isFinite).

`Duration` может быть создана следующим образом:

1. Неявно из типов `Int` и `Long`. Например, `val d = 100 millis`;

2. Передав `Long` и `java.util.concurrent.TimeUnit`. Например, `val d = Duration(100, MILLISECONDS)`;

3. Спарсив строку, представляющей период времени. Например, `val d = Duration(«1,2 µs»)`.

Длительность также предоставляет методы `unapply`, поэтому их можно использовать в конструкциях, соответствующих шаблону. Примеры:

```scala
    import scala.concurrent.duration._
    import java.util.concurrent.TimeUnit._
    
    // конкретизации
    val d1 = Duration(100, MILLISECONDS) // from Long and TimeUnit
    val d2 = Duration(100, "millis")     // from Long and String
    val d3 = 100 millis                  // implicitly from Long, Int or Double
    val d4 = Duration("1.2 µs")          // from String
    
    // pattern matching
    val Duration(length, unit) = 5 millis
```

_Если этот проект окажется полезным тебе - нажми на кнопочку **`★`** в правом верхнем углу._

[<= содержание](https://github.com/steklopod/Parallel-Programming/blob/master/readme.md)