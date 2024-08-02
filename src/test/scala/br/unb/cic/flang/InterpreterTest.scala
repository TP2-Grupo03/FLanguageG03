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
  val initialState: S = List()

  /**"eval CInt(5)" should "return an integer value 5." in {
    val c5 = CInt(5)
    runEval(c5, declarations) should be (Right(5))
  }*/

  "eval Add(CInt(5), CInt(10)) " should "return an integer value 15." in {
    val c5  = CInt(5)
    val c10 = CInt(10)
    val add = Add(c5, c10)
    runEval(add, declarations) should be (Right(15))
  }

  "eval Add(CInt(5), Add(CInt(5), CInt(10))) " should "return an integer value 20." in {
    val c5 = CInt(5)
    val c10 = CInt(10)
    val add = Add(c5, Add(c5, c10))
    runEval(add, declarations) should be(Right(20))
  }

  "eval Mul(CInt(5), CInt(10))" should "return an integer value 50" in {
    val c5 = CInt(5)
    val c10 = CInt(10)
    val mul = Mul(c5, CInt(10))
    runEval(mul, declarations) should be(Right(50))
  }

  "eval App(inc, 99) " should "return an integer value 100" in {
    val app = App("inc", CInt(99))
    runEval(app, declarations) should be (Right(100))
  }

  "eval App(foo, 10) " should "raise an error." in {
    val app = App("foo", CInt(10))
    assertError(eval(app, declarations)) should be (true)
  }

  "eval Add(5, App(bug, 10)) " should "raise an error." in {
    val c5  = CInt(5)
    val app = App("bug", CInt(10))
    val add = Add(c5, app)
    assertError(eval(add, declarations)) should be (true)
  }
  "eval CInt(5)" should "return an integer value 5." in {
    val c5 = CInt(5)
    val (res, _) = runState(eval(c5, declarations))(initialState)
    res should be (Right(IntValue(5)))
  }
  "eval IfThenElse(true, CInt(5), CInt(10))" should "return an integer value 5" in {
  val expr = IfThenElse(CBool(true), CInt(5), CInt(10))
  val (res, _) = runState(eval(expr, declarations))(initialState)
  res should be (Right(IntValue(5)))
  }

  "eval IfThenElse(false, CInt(5), CInt(10))" should "return an integer value 10" in {
    val expr = IfThenElse(CBool(false), CInt(5), CInt(10))
    val (res, _) = runState(eval(expr, declarations))(initialState)
    res should be (Right(IntValue(10)))
  }

  "eval And(CBool(true), CBool(false))" should "return a boolean value false" in {
    val expr = And(CBool(true), CBool(false))
    val (res, _) = runState(eval(expr, declarations))(initialState)
    res should be (Right(BoolValue(false)))
  }

  "eval Or(CBool(true), CBool(false))" should "return a boolean value true" in {
    val expr = Or(CBool(true), CBool(false))
    val (res, _) = runState(eval(expr, declarations))(initialState)
    res should be (Right(BoolValue(true)))
  }

  "eval Not(CBool(true))" should "return a boolean value false" in {
    val expr = Not(CBool(true))
    val (res, _) = runState(eval(expr, declarations))(initialState)
    res should be (Right(BoolValue(false)))
  }

  "eval IfThenElse(App(bug, 10), CInt(5), CInt(10))" should "raise an error" in {
    val app = App("bug", CInt(10))
    val expr = IfThenElse(app, CInt(5), CInt(10))
    assertError(eval(expr, declarations)) should be (true)
  }

  "eval And(CBool(true), App(bug, 10))" should "raise an error" in {
    val app = App("bug", CInt(10))
    val expr = And(CBool(true), app)
    assertError(eval(expr, declarations)) should be (true)
  }

  "eval Or(CBool(true), App(bug, 10))" should "raise an error" in {
    val app = App("bug", CInt(10))
    val expr = Or(CBool(true), app)
    assertError(eval(expr, declarations)) should be (true)
  }

  "eval Not(App(bug, 10))" should "raise an error" in {
    val app = App("bug", CInt(10))
    val expr = Not(app)
    assertError(eval(expr, declarations)) should be (true)
  }
}

