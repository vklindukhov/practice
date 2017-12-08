

//data class - case class. creation without new operator

// inheritance only inside of a file
sealed trait UserTrait

case class LoggedInUser(name: String, surname: String)
case class AnonymousUser(id: Int)

val user: UserTrait

//user match {
//  case LoggedInUser(name, surname) =>
//  case AnonymousUser(id) =>
//}

//sealed trait List[+A] {
//}
//case object Nil extends List[Nothing]
//case class ::[A](head: A, tail: List[A]) extends List[A]


//left and right associative operations - Nil.::(3).::(2).::(1)
//Nil.::(3).::(2).::(1)
1 :: 2 :: 3 :: Nil

trait InnerWorkOfTraitsExample {
  def traitMethod(i: Int): Int
}
//
//object InnerWorkOfTraitsExample$class {
//  def traitMethod($this: InnerWorkOfTraitsExample, i: Int): Int = 123
//}