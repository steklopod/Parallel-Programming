package future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Future1 extends App {
  val printFuture = Future {
    println("Привет из будущего.")
    Thread.sleep(700)
    println(".")
    Thread.sleep(700)
    println(" .")
    Thread.sleep(700)
    println("  .")
    Thread.sleep(700)
    println("    Привет после паузы")

  }
  Await.result(printFuture, Duration.Inf)
}

object Future2 extends App {
  val calcFuture = Future {
    Thread.sleep(1000)
    666
  }
  val stringFuture = Future { "Hello from second Future" }

  val f = for {
    calcFuture <- calcFuture
    stringFuture <- stringFuture
  } yield {
    println(s"$calcFuture $stringFuture")
  }

  Await.result(f, Duration.Inf)
}
