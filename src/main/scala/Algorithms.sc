def fact(n: Int, acc: BigInt = 1): BigInt = {
  if (n <= 1) acc
  else fact(n - 1, acc * n)
}

def abs(x: Double) = if (x < 0) -x else x

def sqrt(x: Double) = {
  def sqrtIter(guess: Double, x: Double): Double = {
    if (isGoodEnough(guess, x)) guess
    else sqrtIter(improve(guess, x), x)
  }
  def isGoodEnough(guess: Double, x: Double) = {
    abs(guess * guess - x) / x < 0.001
  }
  def improve(guess: Double, x: Double) = {
    (guess + x / guess) / 2
  }
  sqrtIter(1, x)
}


def isort(xs: List[Int]): List[Int] = {
  xs match {
    case Nil => Nil
    case x :: xs1 => insert(x, isort(xs1))
  }

  def insert(x: Int, xs: List[Int]): List[Int] = {
    xs match {
      case Nil => List(x)
      case y :: xs1 =>
        if (y >= x) x :: xs
        else y :: insert(x, xs1)
    }
  }
}

isort(List(1, 6, 4, -2, 5, 12))




