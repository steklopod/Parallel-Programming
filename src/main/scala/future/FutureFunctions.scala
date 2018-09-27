package future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

object FutureFunctions extends App {
  //map
  val десять = Future(5) map (2 *) map println
  val сОшибкой = Future(5) map (_ / 0) map println

  Await.result(десять, Duration.Inf)

  val безопасныйСпособ = for {
    ошибка ← сОшибкой
  } yield ошибка
  безопасныйСпособ

  //flatMap
  def f(a: Int): Future[Int] = Future(a * 5)

  val flat = Future(2) flatMap f map println
  Await.result(flat, Duration.Inf)

}
