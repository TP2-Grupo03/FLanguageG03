package br.unb.cic.flang

import MErr._
import MErr.eh._

import Declarations._
import Substitution._

object Interpreter {
  def eval(expr: Expr, declarations: List[FDeclaration]): MError[Either[Integer, Boolean]] = expr match {
    case CInt(v) => 
      println(s"Evaluating CInt($v)")
      pure(Left(v))
    case CBool(v) =>
      println(s"Evaluating CBool($v)")
      pure(Right(v))
    case Add(lhs, rhs) => for {
      l <- eval(lhs, declarations).map(_.left.getOrElse(throw new Exception("Expected integer")))
      r <- eval(rhs, declarations).map(_.left.getOrElse(throw new Exception("Expected integer")))
      _ = println(s"Adding $l and $r")
    } yield Left(l + r)
    case Mul(lhs, rhs) => for {
      l <- eval(lhs, declarations).map(_.left.getOrElse(throw new Exception("Expected integer")))
      r <- eval(rhs, declarations).map(_.left.getOrElse(throw new Exception("Expected integer")))
      _ = println(s"Multiplying $l and $r")
    } yield Left(l * r)
    case Id(v) => 
      println(s"Error evaluating an identifier: $v")
      raiseError("Error evaluating an identifier.")
    case FApp(n, arg) => for {
      fdecl <- lookup(n, declarations)
      _ = println(s"Function application of $n with argument $arg")
      bodyS = substitute(arg, fdecl.arg, fdecl.body)
      res <- eval(bodyS, declarations)
    } yield res
    case IfThenElse(cond, thenExpr, elseExpr) => for {
      condValue <- eval(cond, declarations).map(_.right.getOrElse(throw new Exception("Expected boolean")))
      _ = println(s"If condition evaluates to $condValue")
      res <- if (condValue) eval(thenExpr, declarations) else eval(elseExpr, declarations)
    } yield res
    case And(lhs, rhs) => for {
      l <- eval(lhs, declarations).map(_.right.getOrElse(throw new Exception("Expected boolean")))
      r <- eval(rhs, declarations).map(_.right.getOrElse(throw new Exception("Expected boolean")))
      _ = println(s"Logical AND between $l and $r")
    } yield Right(l && r)
    case Or(lhs, rhs) => for {
      l <- eval(lhs, declarations).map(_.right.getOrElse(throw new Exception("Expected boolean")))
      r <- eval(rhs, declarations).map(_.right.getOrElse(throw new Exception("Expected boolean")))
      _ = println(s"Logical OR between $l and $r")
    } yield Right(l || r)
    case Not(expr) => for {
      e <- eval(expr, declarations).map(_.right.getOrElse(throw new Exception("Expected boolean")))
      _ = println(s"Logical NOT of $e")
    } yield Right(!e)
  }

  def runEval(expr: Expr, declarations: List[FDeclaration]): Either[String, Either[Integer, Boolean]] = {
    eval(expr, declarations).value.runA(Nil).value
  }
}
