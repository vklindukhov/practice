trait Trait1 {
  def traitMethod: Int = 1
}

trait Trait2 extends Trait1 {
  override def traitMethod: Int = 2
}

trait Trait3 extends Trait1 {
  override def traitMethod: Int = 3
}

class SomeClass extends Trait2 with Trait3

val sc = new SomeClass

sc.traitMethod