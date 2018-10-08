package scalashop

import org.scalameter._
import common._

object HorizontalBoxBlurRunner {

  val standardConfig: MeasureBuilder[Unit, Double] = config(
    Key.exec.minWarmupRuns -> 5,
    Key.exec.maxWarmupRuns -> 10,
    Key.exec.benchRuns -> 10,
    Key.verbose -> true) withWarmer new Warmer.Default

  def main(args: Array[String]): Unit = {
    val radius = 3
    val width = 1920
    val height = 1080
    val src = new Img(width, height)
    val dst = new Img(width, height)
    val seqtime = standardConfig measure {
      HorizontalBoxBlur.blur(src, dst, 0, height, radius)
    }
    println(s"sequential blur time: $seqtime ms")

    val numTasks = 32
    val partime = standardConfig measure {
      HorizontalBoxBlur.parBlur(src, dst, numTasks, radius)
    }
    println(s"fork/join blur time: $partime ms")
//    println(s"speedup: ${seqtime / partime}")
  }
}

/** A simple, trivially parallelizable computation. */
object HorizontalBoxBlur {

  /**
    * Blurs the rows of the source image `src` into the destination image `dst`,
    *  starting with `from` and ending with `end` (non-inclusive).
    *
    *  Within each row, `blur` traverses the pixels by going from left to right.
    */
  def blur(src: Img, dst: Img, from: Int, end: Int, radius: Int): Unit = {
    for {
      y <- from until end // for each row
      x <- 0 until src.width // going from left to right
      if y >= 0 && y < src.height // No need to check x as it is specifically defined
    } yield {
      val rgba = boxBlurKernel(src, x, y, radius)
      dst.update(x, y, rgba)
    }
  }

  /**
    * Blurs the rows of the source image in parallel using `numTasks` tasks.
    *
    *  Parallelization is done by stripping the source image `src` into
    *  `numTasks` separate strips, where each strip is composed of some number of
    *  rows.
    */
  def parBlur(src: Img, dst: Img, numTasks: Int, radius: Int): Unit = {
    // Get the numbers we need for parallelization
    val allRows = 0 until src.height // indexes of all the rows in the image
    val rowsPerTask = math.max(src.height / numTasks, 1) // make sure we have atleast 1 row
    val startRows = allRows by rowsPerTask // rows where we split for starting separate tasks

    // parallelization
    val allTasks = startRows.map(elem => {
      task {
        // do elem to (elem + rowsPerTask) rows in one task
        blur(src, dst, elem, elem + rowsPerTask, radius)
      }
    })

    // finally join
    allTasks.foreach(task => task.join()) // each element of allTasks is a task
  }

}
