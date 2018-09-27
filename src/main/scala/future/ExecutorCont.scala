package future

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}

object ExecutorCont extends App{

  val e1 = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))
  implicit val e2 = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))

  val calcFuture = Future {
    Thread.sleep(1000); 1000
  }

}

object FutureCompanion extends App {
  var futures = for { i <- 1 to 10 } yield Future.successful()
}