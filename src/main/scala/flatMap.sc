val ls: List[Int] = List(0, 1)
ls.flatMap(x => ls.flatMap(y => List(x, y)))
ls.map(x => ls.flatMap(y => List(x, y)))
ls.flatMap(x => ls.map(y => List(x, y)))
ls.map(x => ls.map(y => List(x, y)))

ls.map(x => ls.map(y => (x, y)))
ls.flatMap(x => ls.map(y => (x, y)))
ls.flatMap(x => ls.flatMap(y => List((x, y))))

def cartesianPow[T](alphabet: List[T], pow: Int): List[List[T]] = {
  if (pow == 0) List(Nil)
  else if (pow == 1) alphabet.map(List(_))
  else cartesianPow(alphabet, pow - 1).flatMap(tail => alphabet.map(head => head :: tail))
}

//list.permutations.toList
def permutations[T](list: List[T]): List[List[T]] = {
  if (list.isEmpty) List(Nil)
  else if (list.tail.isEmpty) List(list)
  else list.indices.flatMap(i => permutations(list.patch(i, Nil, 1)).map(list(i) :: _)).toList
}

def subSets[T](list: List[T]): List[List[T]] = {
  if (list.isEmpty) List(Nil)
  else if (list.tail.isEmpty) List(Nil, list)
  else subSets(list.tail).flatMap(e => List(e, list.head :: e))
}

def partitions[T](list: List[T]): List[List[List[T]]] = {
  if (list.isEmpty) List(List(Nil))
  else List(list) :: (1 until list.length).flatMap(e => partitions(list drop e).map(list.take(e) :: _)).toList
}

def partitionsWithOperators(list: List[Int], endEqCondition: Int) = {
  def eval(list: List[List[Int]]): Int = {
    list.map(_.product).sum
  }
  def toString: (List[List[Int]]) => String = {
    _.map(_.mkString("*")).mkString(" + ")
  }
  partitions(list)
    //    .filter(eval(_) == endEqCondition)
    .map(toString)
}

def pearl6ByRichardBird(list: List[Int], endEqCondition: Int) = {
  //  def evalMinusPlus(list: List[List[Int]]): Int = {
  //    list.map(internalList => internalList.tail.foldLeft(internalList.head)(_ - _)).sum
  //  }

  def evalProductPlus(list: List[List[Int]]): Int = {
    list.map(_.product).sum
  }

  def toString: (List[List[Int]]) => String = {
    _.map(_.mkString("*")).mkString(" + ")
  }

  subSets(list)
    .flatMap(permutations(_).flatMap(partitions))
    .filter(evalProductPlus(_) == endEqCondition)
    .map(toString)
}

def pearlByRichardBird() = {
  partitions(List(1, 2, 3, 4, 5, 6, 7, 8, 9))
    .flatMap(partitions)
    .filter(x => x.map(y => y.map(z => z.reduce(10 * _ + _)).product).sum == 100)
    .map(_.map(_.map(_.mkString("")).mkString("*")).mkString(" + "))
    .foreach(println)
}

cartesianPow(List(0, 1), 2).foreach(println)
//permutations(List(1, 2, 3)).foreach(println)
//subSets(List(0, 1, 2, 3)).foreach(println)
//partitions(List(0, 1, 2, 3)).foreach(println)
//partitionsWithOperators(List(0, 1, 2, 3), 6).foreach(println)
//pearl6ByRichardBird(List(1, 2, 3, 4), 10).foreach(println)
pearlByRichardBird

//val A: List[String] = List("a", "b", "c", "d", "e", "f", "g")
//val B: List[Int] = List(0, 1, 2, 3, 4, 5)
//
//A.flatMap(x => {B.map(y => (x, y))}).foreach(println)


//A = {a,b,c,d,e,f,g}
//B = {0,1,2,3,4,5}
//
//(A, B) = {(a,b) | a < A, b < B } = {(a,0), (a,1), (b,0), (b,1), (c,0), (c,1)}
//|(A, B)| = |A| * |B| = 3 * 2 = 6

//
//A*B*C*D = {0, 1} * {0, 1} * {0, 1}  * {0, 1} =
//  {(a,b,c,d) | a < A, b < B, c < C, d < D} = {...}
//
//
//A^n = A*.....*A
//      |_______|
//          n
