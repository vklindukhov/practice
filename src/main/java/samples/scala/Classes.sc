
// No static in classes
class ScalaClass(i: Int) {
  def this(s: String) {
    this(s.toInt)
  }

  def double: Int = i * 2
  ScalaClass.method
}

//Singleton object for static, companion for ScalaClass class
object ScalaClass {
 private def method = 42
}

//like interface in Java, no constructor
trait ScalaTrait {
  def traitMethod = 42
}