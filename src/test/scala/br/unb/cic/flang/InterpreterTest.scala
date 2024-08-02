package br.unb.cic.flang

import org.scalatest._
import flatspec._
import matchers._

import Interpreter._
import Declarations._
import MErr._

class InterpreterTest extends AnyFlatSpec with should.Matchers {

  val inc = FDeclaration("inc", "x", Add(Id("x"), CInt(1)))
  val bug = FDeclaration("bug", "x", Add(Id("y"), CInt(1)))

  val declarations = List(inc, bug)

  "eval CInt(5)" should "return an integer value 5." in {
    val c5 = CInt(5)
    runEval(c5, declarations) should be (Right(Left(5)))
  }

  "eval Add(CInt(5), CInt(10)) " should "return an integer value 15." in {
    val c5  = CInt(5)
    val c10 = CInt(10)
    val add = Add(c5, c10)
    runEval(add, declarations) should be (Right(Left(15)))
  }

  "eval Add(CInt(5), Add(CInt(5), CInt(10))) " should "return an integer value 20." in {
    val c5 = CInt(5)
    val c10 = CInt(10)
    val add = Add(c5, Add(c5, c10))
    runEval(add, declarations) should be(Right(Left(20)))
  }

  "eval Mul(CInt(5), CInt(10))" should "return an integer value 50" in {
    val c5 = CInt(5)
    val c10 = CInt(10)
    val mul = Mul(c5, CInt(10))
    runEval(mul, declarations) should be(Right(Left(50)))
  }

  "eval FApp(inc, CInt(99)) " should "return an integer value 100" in {
    val fapp = FApp("inc", CInt(99))
    runEval(fapp, declarations) should be (Right(Left(100)))
  }

  "eval FApp(foo, CInt(10)) " should "raise an error." in {
    val fapp = FApp("foo", CInt(10))
    assertError(eval(fapp, declarations)) should be (true)
  }

  "eval Add(CInt(5), FApp(bug, CInt(10))) " should "raise an error." in {
    val c5  = CInt(5)
    val fapp = FApp("bug", CInt(10))
    val add = Add(c5, fapp)
    assertError(eval(add, declarations)) should be (true)
  }

  "eval IfThenElse(CBool(true), CInt(10), CInt(20))" should "return 10" in {
    val expr = IfThenElse(CBool(true), CInt(10), CInt(20))
    runEval(expr, declarations) should be (Right(Left(10)))
  }

  "eval IfThenElse(CBool(false), CInt(10), CInt(20))" should "return 20" in {
    val expr = IfThenElse(CBool(false), CInt(10), CInt(20))
    runEval(expr, declarations) should be (Right(Left(20)))
  }
}
