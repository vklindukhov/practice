println(List(1, 2, 3).reduceLeft(10*_ + _))
println(List(1, 2, 3).reduceRight(10*_ + _))
println(List(1, 2, 3).reduceLeft(_ - _))
println(List(1, 2, 3).reduceRight(_ - _))