package funsets

import org.scalatest.FunSuite


import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {

  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  // test("string take") {
  //   val message = "hello, world"
  //   assert(message.take(5) == "hello")
  // }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
   // test("adding ints") {
   //   assert(1 + 2 === 3)
   // }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements of each set") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersect contains elements in both set") {
    new TestSets {
      val s = intersect(union(s1, s2), union(s2, s3))
      assert(! contains(s, 1), "Intersect 1")
      assert(  contains(s, 2), "Intersect 2")
      assert(! contains(s, 3), "Intersect 3")
    }
  }

  test("diff contains elements only in first set") {
    new TestSets {
      val s = diff(union(s1, s2), union(s2, s3))
      assert(  contains(s, 1), "Diff 1")
      assert(! contains(s, 2), "Diff 2")
      assert(! contains(s, 3), "Diff 3")
    }
  }

  test("filter contains elements satisfying p") {
    new TestSets {
      def p(ele: Int): Boolean = ele >= 2
      val s = filter(union(union(s1, s2), s3), p)
      assert(! contains(s, 1), "Filter 1")
      assert(  contains(s, 2), "Filter 2")
      assert(  contains(s, 3), "Filter 3")
      assert(! contains(s, 4), "Filter 1")
    }
  }

  test("forall tests if all elements satisfying p") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      def p1(ele: Int): Boolean = ele > 1
      def p2(ele: Int): Boolean = ele > 0
      assert(! forall(s, p1), "all elements greater than 1")
      assert(  forall(s, p2), "all elements greater than 0")
    }
  }

  test("exists tests if any element satisfying p") {
    new TestSets {
      val s = union(union(s1, s2), s3)
      def p1(ele: Int): Boolean = ele > 2
      def p2(ele: Int): Boolean = ele > 3
      assert(  exists(s, p1), "any elements greater than 2")
      assert(! exists(s, p2), "any elements greater than 3")
    }
  }

  test("map transform all elements into new set using function f") {
    new TestSets {
      val s = union(s1, s2)
      def f(a: Int): Int = a * 2
      val ret = map(s, f)
      assert(! contains(ret, 1), "Not has 1")
      assert(  contains(ret, 2), "Has 2")
      //assert(  contains(ret, 6), "Has 6")
      assert(! contains(ret, 0), "Not has 0")
    }
  }
}
