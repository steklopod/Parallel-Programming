package future

import scala.concurrent.Future

object FutureCOmpanion  extends App{

  var futures = for{i <-1 to 10 } yield Future.successful()
}
