package lacasa.neg

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Test

import lacasa.util._

@RunWith(classOf[JUnit4])
class NegSpec {
  @Test
  def test1() {
    expectError("class") {
      """
        class C {}
        class C {}
      """
    }
  }

  @Test
  def test2() {
    expectError("insecure") {
      """
object C {
  var danger = 5
}

class C {
  val f = 5
  def m(x: Int): Unit = {
    C.danger = 10
  }
}"""
    }
  }

  @Test
  def test3() {
    expectError("class E") {
      """
object C {
  var danger = 5
}

class C {
  val f = 5
  def m(x: Int): Unit = {
    C.danger = 10
  }
}

class E extends D {
  val h = 15
}

class D extends C {
  val g = 10
}"""
    }
  }

  @Test
  def test4() {
    expectError("insecure") {
      """
object C {
  val nodanger = 10
  var danger = 5
}

class C {
  val f = 5
  def m(x: Int): Unit = {
    val v1 = C.nodanger
    val v2 = C.danger
  }
}"""
    }
  }

  @Test
  def test5() {
    expectError("insecure") {
      """
class C {
  val f = 5
  def m(x: Int): Unit = {
    val v1 = C.someMethod(5)
  }
}
object C {
  var danger = 5
  def someMethod(x: Int) = x + danger
}"""
    }
  }

  @Test
  def test6() {
    expectError("insecure") {
      """
object C {
  var danger = 5
  def someMethod(x: Int) = x + danger
}
class C {
  val f = 5
  def m(x: Int): Unit = {
    val v1 = C.someMethod(5)
  }
}"""
    }
  }

}