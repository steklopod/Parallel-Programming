package future

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import java.lang.Thread._

object ForCompr extends App {

  val f1 = Future { sleep(800); 1 }
  val f2 = Future { sleep(200); 2 }
  val f3 = Future { sleep(400); 3 }

  val result = for {
    r1 <- f1
    r2 <- f2
    r3 <- f3
  } yield r1 + r2 + r3

  result.onComplete {
    case Success(x) => println(s"\n Сумма = $x")
    case Failure(e) => e.printStackTrace
  }

  sleep(3000) // важно! для небольшой параллельной демонстрации: сохранить jvm в живых
}


object ForCompr2 extends App {
  val calcFuture = Future {
    sleep(1000)
    666
  }
  val stringFuture = Future { "Hello from second Future" }

  val f = for {
    calcFuture <- calcFuture
    stringFuture <- stringFuture
  } yield {
    println(s"$calcFuture $stringFuture")
  }

  sleep(3000) //не даем JVM умереть
}