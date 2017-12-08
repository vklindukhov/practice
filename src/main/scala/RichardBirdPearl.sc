
// 123 => (1,2,3)
// 123*456 => ((1,2,3), (4,5,6))
// 123*456 + 789 => (((1,2,3), (4,5,6)), ((7,8,9)))

def partitions[T](list: List[T]): List[List[List[T]]] = {
  if (list.isEmpty) List(List(Nil))
  else List(list) :: (1 until list.length).flatMap(e => partitions(list drop e).map(list.take(e) :: _)).toList
}

def partitions2[T](list: List[T]): List[List[List[List[T]]]] = partitions(list).flatMap(partitions)


// (((1,2,3), (4,5,6)), ((7,8,9))) => 123*456 + 789
def evalSum(threeList: List[List[List[Int]]]): Int = threeList.map(evalProd).sum
// ((1,2,3), (4,5,6)) => (123, 456) => 123*456
def evalProd(twoList: List[List[Int]]): Int = twoList.map(evalEmpty).product
// (1,2,3) => ((10*1 + 2), 3) => 10*(10*1 + 2) + 3  == 123
def evalEmpty(list: List[Int]): Int = list.reduceLeft(10 * _ + _)


// (((1,2,3), (4,5,6)), ((7,8,9))) => "123*456 + 789"
def toStrSum(threeList: List[List[List[Int]]]): String = threeList.map(toStrProd).mkString(" + ")
// ((1,2,3), (4,5,6)) => "123*456"
def toStrProd(twoList: List[List[Int]]): String = twoList.map(toStrEmpty).mkString("*")
// (1,2,3) => "123"
def toStrEmpty(list: List[Int]): String = list.mkString("")



partitions2(List(1, 2, 3, 4, 5, 6, 7, 8, 9))
  .filter(evalSum(_) == 101)
  .map(toStrSum)
  .foreach(println)










//





















