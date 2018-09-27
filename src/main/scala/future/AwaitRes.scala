package future

import java.lang.Thread._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util._

object Future1 extends App {
  val printFuture = Future {
    println("Привет из будущего.")
    sleep(700)
    println(".")
    sleep(700)
    println(" .")
    sleep(700)
    println("  .")
    sleep(700)
    println("    Привет после паузы")

  }
  sleep(3000) //не даем JVM умереть
}

object Future2 extends App {
  println("starting calculation ...")
  val f = Future {
    sleep(Random.nextInt(500))
    42
  }
  println("before onComplete")

  f.onComplete {
    case Success(value) => println(s"Got the callback, meaning = $value")
    case Failure(e)     => e.printStackTrace
  }
  // do the rest of your work
  println("A ..."); sleep(100)
  println("B ..."); sleep(100)
  println("C ..."); sleep(100)
  println("D ..."); sleep(100)
  println("E ..."); sleep(100)
  println("F ..."); sleep(100)
  sleep(2000)
}

object Future3 extends App {
  val a = Future { sleep(1000); 1 }
  val b = Future { sleep(100); 2 }
  val c = Future { sleep(10); 3 }

  val result = Future.firstCompletedOf(Seq(a, b, c))

  result.onComplete {
    case Success(value) => println(s"Значение первого завершившегося фьючерса = $value")
    case Failure(e)     => e.printStackTrace
  }
  sleep(3000) //не даем JVM умереть

}