object Tester {
  def nth[T](i: Int, list: List[T]): T = {
    if (list.isEmpty) throw new IndexOutOfBoundsException
    else if (i == 0) list.head
    else nth(i - 1, list.tail)
  }
}
val list = new Cons(1, new Cons(2, new Cons(3, new Cons(5, new Cons(2, new Nil)))))
Tester.nth(5, list)

abstract class IntSet {
  def contains(elem: Int): Boolean

  def incl(elem: Int): IntSet

  def union(other: IntSet): IntSet
}

object Empty extends IntSet {
  def contains(elem: Int): Boolean = false

  def incl(elem: Int): IntSet = new NonEmpty(elem, Empty, Empty)

  override def toString() = "."

  def union(other: IntSet): IntSet = other
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean = {
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true
  }

  def incl(x: Int): IntSet = {
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this
  }

  override def toString: String = "{" + left + elem + right + "}"

  def union(other: IntSet): IntSet = {
    ((left union right) union other) incl elem
  }
}


trait List[T] {
  def isEmpty: Boolean

  def head: T

  def tail: List[T]
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty: Boolean = false


}

class Nil[T] extends List[T] {
  def isEmpty: Boolean = true

  def head: Nothing = throw new NoSuchElementException("Nil.head")

  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}