
"Scala generics" compareTo "Java generics"

//Co-variancy and Contra-variancy
class GenericFrom[-CONTRA, +CO, IN] {
}

//Functional type
//Int => Int - Function1[Int, Int]
val inc: Int => Int = _ + 1
inc(1) /*inc.apply(1)*/

val inc2 = (x: Int) => x + 1

val sum: (Nothing, Nothing) => AnyVal = (x: Int, y: Int) => x + y
