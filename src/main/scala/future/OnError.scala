package future

import scala.concurrent.Future

class OnError {}

object Future7 extends App{
  val calcFailureFuture = Future.failed(new RuntimeException)
  val calcSuccess = Future.successful(1)

  println(calcFailureFuture.value)       //Some(Failure(java.lang.RuntimeException))
  println(calcFailureFuture.failed.value)//Some(Success(java.lang.RuntimeException))
  println(calcFailureFuture.isCompleted) //true
  println(calcSuccess.value)             //Some(Success(1))

}