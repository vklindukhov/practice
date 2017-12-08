val A: Array[Int] = Array(1, 2, 3, 4, 5)
val B: Array[String] = Array("a", "b", "c", "d", "e")
val C: Array[String] = Array("@", "#", "$", "%")

for (e <- A if 0 < e if e < 10 if e % 2 == 0; x <- B) {
  println("(" + e + ", " + x + ")")
}


for {
  x <- A if 0 < x if x < 10 if x % 2 == 0
  y <- B
  z <- C
} println("(" + x + ", " + y + ", " + z + ")")

A.withFilter(0 < _).withFilter(_ < 10).withFilter(_ % 2 == 0).foreach(x =>
  B.foreach(y =>
    C.foreach(z =>
      println("(" + x + ", " + y + ", " + z + ")")
    )
  )
)

for {
  x <- A if 0 < x if x < 10 if x % 2 == 0
  y = x * x
  z = y * y
} println("(" + x + ", " + y + ", " + z + ")")


val list = for {
  x <- A if 0 < x if x < 10 if x % 2 == 0
  y <- B
  z <- C
} yield  "(" + x + ", " + y + ", " + z + ")"

list.foreach(println)

val list2 = for {
  x <- A if 0 < x if x < 10 if x % 2 == 0
  y = x * x
  z = y * y
} yield "(" + x + ", " + y + ", " + z + ")"

list2.foreach(println)