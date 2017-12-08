
val list = 1 :: 2 :: 3 :: Nil

val squares = list.map(x => x * x).map(x => x + 1)

list.view.map(x => x * x).map(x => x + 1)

squares.filter(_ % 2 == 0)

list.flatMap(x => List.fill(x)(x))

list.updated(1, 3)
list

val a = Array(1, 2, 3, 4, 5)
a.deep.mkString(", ")

a(1) = 3
a.deep.mkString(", ")