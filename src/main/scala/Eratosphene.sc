object Eratosphene {
  def from(n: Int): Stream[Int] = n #:: from(n + 1)

  def eratoshpene(s: Stream[Int]): Stream[Int] =
    s.head #:: eratoshpene(s.tail.filter(_ % s.head != 0))

  def primes = eratoshpene(from(2))

  def primesTo = (primes take 25).toList
  primesTo
}
