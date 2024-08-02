package br.unb.cic.flang

import org.scalatest._
import flatspec._
import matchers._

import Interpreter._
import Declarations._
import MErr._

class IfThenElseTests extends AnyFlatSpec with should.Matchers {

  val inc = FDeclaration("inc", "x", Add(Id("x"), CInt(1)))
  val bug = FDeclaration("bug", "x", Add(Id("y"), CInt(1)))
  val mulTwo = FDeclaration("mulTwo", "x", Mul(Id("x"), CInt(2)))
  val declarations = List(inc, bug, mulTwo)

  "eval IfThenElse(CBool(true), CInt(10), CInt(20))" should "return 10" in {
    val expr = IfThenElse(CBool(true), CInt(10), CInt(20))
    runEval(expr, declarations) should be(Right(Left(10)))
  }

  "eval IfThenElse(CBool(false), CInt(10), CInt(20))" should "return 20" in {
    val expr = IfThenElse(CBool(false), CInt(10), CInt(20))
    runEval(expr, declarations) should be(Right(Left(20)))
  }

  "eval IfThenElse(CBool(true), Add(CInt(5), CInt(5)), Mul(CInt(2), CInt(3)))" should "return 10" in {
    val expr = IfThenElse(CBool(true), Add(CInt(5), CInt(5)), Mul(CInt(2), CInt(3)))
    runEval(expr, declarations) should be(Right(Left(10)))
  }

  "eval IfThenElse(CBool(false), Add(CInt(5), CInt(5)), Mul(CInt(2), CInt(3)))" should "return 6" in {
    val expr = IfThenElse(CBool(false), Add(CInt(5), CInt(5)), Mul(CInt(2), CInt(3)))
    runEval(expr, declarations) should be(Right(Left(6)))
  }

  "eval IfThenElse(CBool(true), FApp('inc', CInt(10)), CInt(0))" should "return 11" in {
    val expr = IfThenElse(CBool(true), FApp("inc", CInt(10)), CInt(0))
    runEval(expr, declarations) should be(Right(Left(11)))
  }

  "eval IfThenElse(CBool(false), CInt(0), FApp('inc', CInt(10)))" should "return 11" in {
    val expr = IfThenElse(CBool(false), CInt(0), FApp("inc", CInt(10)))
    runEval(expr, declarations) should be(Right(Left(11)))
  }
}
