package promise

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future, Promise}

class FuturePractic {
}

object ThreadApp extends App {

  implicit class FutureExt[T](val f: Future[T]) extends AnyVal {
    def ixists(p: T => Boolean): Future[Boolean] = {
      val promise = Promise[Boolean]()
      f.onComplete(t => promise.complete(t.map(p)))
      promise.future
    }
  }

  println(Thread.currentThread().getName)

  val f = Future {
        println(Thread.currentThread().getName)
    1
  }

  val result = Await.result(f, Duration.Inf)

  println(result)
}
