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

  "eval And(CInt(10), CInt(5)))" should "raise an error" in {
    val c10 = CInt(10)
    val c5 = CInt(5)
    val expr = And(c5, c10)
    assertError(eval(expr, declarations)) should be (true)
  }

  "eval Not(CInt(0))" should "raise an error" in {
    val expr = Not(CInt(0))
    assertError(eval(expr, declarations)) should be (true)
  }

  "eval App(inc, CInt(5))" should "return an integer value 6" in {
    val expr = App("inc", CInt(5))
    val (res, _) = runState(eval(expr, declarations))(initialState)
    res should be (Right(IntValue(6)))
  } 

  "eval App(inc, CInt(4))" should "alter the state during evaluation" in {
    val expr = App("inc", CInt(4))
    val computation: MError[ValueType] = eval(expr, declarations)
    val (res, finalState) = runState(computation)(initialState)
    res should be(Right(IntValue(5)))
    finalState should not be(initialState)
  }

  "eval Add(CInt(3), CInt(4))" should "not alter the state during evaluation" in {
    val expr = Add(CInt(3), CInt(4))
    val computation: MError[ValueType] = eval(expr, List())
    val (res, finalState) = runState(computation)(initialState)
    res should be(Right(IntValue(7)))
    finalState should be(initialState)
  }
  
  "eval App(inc, CBool(false))" should "raise an error and not alter the state during evaluation" in {
    val expr = App("inc", CBool(false))
    val res: Either[String, Any] = runEval(expr, declarations)
    res should be (Left("Expected an integer, but got BoolValue(false)"))
  }

}

