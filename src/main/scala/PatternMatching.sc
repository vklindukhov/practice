import scala.annotation.switch

"Pattern matching" match {
  case "Extended switch for literals" =>
    1 match {
      case 1 =>
      case blabla =>
    }

  case "Case classes mathing" =>
    case class A[T](i: T)
    A(A(1)) match {
      case A(A(x)) => x
    }

  case "Optional type matching" =>
    def method(i: Int): Option[Int] = {
      None
    }
    method(7) match {
      case Some(x) =>
      case None =>
    }

  case "List matching" =>
    1 :: 2 :: Nil match {
      case x :: Nil =>
      case ::(x, tail) =>
    }

    class A[T, C]
    val x: Int A Int = new A[Int, Int]

  case _ =>
}

def patternMatherCases() = {
  val target1 = "123"
  val Target1 = "123"
  val B = 'Q'
  val Regexp = "([a-cA-C])".r
  def show(toMatch: Any) = (toMatch: @switch) match {
    case `target1` => println("stable identifier with backticks")
    case Target1 => println("stable identifier with Upper case letter")
    case x @ "abc" => println("name binding")
    case e @ (_: ClassCastException | _: RuntimeException)  => println("name binding with multiple matching")
    case y: Int if y < 0  => println("Guard")
    case 'A' | B  => println("@switched")
    case Regexp => println("RegExp 1")
//    case r"([a-cA-C])" => println("RegExp 2")
    case _ => println("any match")
    case target1 => println("NOT stable identifier, works like _")
  }

  def inFilter() = {
    List(1, 2, 3).withFilter {
      case 1 => true
      case _ => false
    }

    List(1, 2, 3).withFilter(_ match {
      case 1 => true
      case _ => false
    })

    List(1, 2, 3).withFilter(e => e match {
      case 1 => true
      case _ => false
    })
  }

  show(new RuntimeException)
}


patternMatherCases()