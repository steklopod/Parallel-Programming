package future

class PracticFuture{}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object Future1 extends App
{
  val printFuture = Future{
    println("Hello from Future")
  }
  Await.result(printFuture, Duration.Inf)
}

object Future2 extends App{
  val x = ???
}

object Future3 extends App{
  val calcFuture = Future {
    Thread.sleep(1000); 1000
  }
  val stringFuture = Future {"Hello from second Future"}
  val f = for{
    calcFuture <- calcFuture
    stringFuture <- stringFuture
  } yield {
    println(s"$calcFuture $stringFuture")
  }
  //TODO
  //  Await.result()

}


object Future4 extends App{
  val calcFuture = Future {
    Thread.sleep(1000); 1000
  }
  def stringFuture = ???

}