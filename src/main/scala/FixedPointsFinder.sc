

object FixedPointsFinder {
  val delta = 0.0001

  def ifCloseEnough(x: Double, y: Double) = {
    math.abs((x - y) / x) / x < delta
  }

  def fixedPoint(f: Double => Double)(firstGuess: Double) = {
    def loop(guess: Double): Double = {
      val next = f(guess)
      if (ifCloseEnough(guess, next)) next
      else loop(next)
    }
    loop(firstGuess)
  }

  def sqrt(x: Double) = fixedPoint(y => (y + x / y) / 2.0)(1.0)

  def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2

  def sqrtViaAverageDamp(x: Double) = fixedPoint(averageDamp(y => x / y))(1.0)

  //  sqrt(9)
  //    fixedPoint(x => 1 + x/2)(4)
  val list = List(1, 2.3)
  list.foldLeft()
}

