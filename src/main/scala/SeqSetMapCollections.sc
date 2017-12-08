//--------------------------------------SEQ-----------------------------------------------------------------------------

val xs = List(1, 2, -1, 4, 5)
val ys = List("a", "b", "c")
val zs = List((1, 2), (3, 4), (5, 6))
val ls = List(xs, ys, zs)

xs.length
xs.size
xs.head
xs.tail
xs.init
xs.last
xs take 3
xs drop 3
xs(3)

0 :: xs
xs ++ ys
xs ::: ys
xs.reverse
xs updated(3, "@")

xs indexOf 2
xs contains true
xs.exists(e => e < 0)
xs forall (e => -10 < e)

xs filter (0 < _)
xs filter (0 < _)
xs filterNot (0 < _)
xs partition (0 < _)
xs takeWhile (0 < _)
xs dropWhile (0 < _)
xs span (0 < _)
xs zip ys
zs unzip;
xs.flatMap(x => ys.flatMap(y => List(x, y)))
xs.sum
xs.product
xs.max
xs.min
(xs zip xs).map(xy => xy._1 * xy._2).sum
(xs zip xs).map { case (x, y) => x * y }.sum

xs reduceLeft ((x, y) => x - y)
xs reduceRight ((x, y) => x - y)
xs.foldLeft(0)((x, y) => x - y)
xs.foldLeft(11)((x, y) => x - y)
xs.foldRight(0)((x, y) => x - y)
xs.foldRight(-1)((x, y) => x - y)

val str: String = "Hello World"
str filter (c => c.isUpper)
val arr = Array(1, 2, -3, 4, 5, -6, -7)
arr filter (e => e > 0)

val r = 1 until 5
val s = 1 to 5
val t = 1 to 10 by 3
val v = 10 until 0 by -2
(1 to 3).flatMap(x => (1 to 3).map(y => (x, y)))
(1 to 10).map(i => (1 to i).map(j => (i, j))).flatten

def isPrime(n: Int): Boolean = (2 until n) forall (n % _ != 0)
isPrime(7)
(1 until 10)
  .flatMap(i => (1 until i).map(j => (i, j)))
  .filter(pair => isPrime(pair._1 + pair._2))
for {
  i <- 1 until 10
  j <- 1 until i
  if isPrime(i + j)
} yield (i, j)


//--------------------------------------SET-----------------------------------------------------------------------------

object nqueens {
  def queens(n: Int): Set[List[Int]] = {
    def placeQueens(k: Int): Set[List[Int]] = {
      if (k == 0) Set(List())
      else for {
        queens <- placeQueens(k - 1)
        col <- 0 until n
        if isSafe(col, queens)
      } yield col :: queens
    }
    def isSafe(col: Int, queens: List[Int]): Boolean = {
      val row = queens.length
      val queensWithRows = (row - 1 to 0 by -1) zip queens
      queensWithRows forall {
        case (r, c) => col != c && math.abs(col - c) != row - r
      }
    }

    placeQueens(n)
  }
  def show(queens: List[Int]) = {
    val lines =
      for (col <- queens.reverse)
        yield Vector.fill(queens.length)("* ").updated(col, "Q ").mkString
    "\n" + (lines mkString "\n")
  }
}

(nqueens.queens(8) map nqueens.show).mkString("\n\n")

//--------------------------------------MAP-----------------------------------------------------------------------------
val m = Map("one" -> 1, "two" -> 2, "three" -> 3)
m get "one"
m get "four"
m("one")
//m("four") -> NoSuchElementException
val fs = List("apple", "orange", "peach", "pear")
fs sortWith(_.length < _.length)
fs.sorted
fs groupBy(_.head)