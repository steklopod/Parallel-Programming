package future

import java.util.concurrent.Executors

import scala.concurrent.{ExecutionContext, Future}


object ExecutorSer extends App{

  val e1 = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))
  implicit val e2 = ExecutionContext.fromExecutor(Executors.newFixedThreadPool(1))

  val calcFuture = Future {
    Thread.sleep(1000); 1000
  }


}
