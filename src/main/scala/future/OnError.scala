package future

import scala.concurrent.Future

class OnError {}

object Future7 extends App{
  val calcFailureFuture = Future.failed(new RuntimeException)
  val calcSuccess = Future.successful(1)

  println(calcFailureFuture.value)
  println(calcFailureFuture.isCompleted)
  println(calcFailureFuture.failed.value)
  println(calcSuccess.value) //Some(Success(1))

}