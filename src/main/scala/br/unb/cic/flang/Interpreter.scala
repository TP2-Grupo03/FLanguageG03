package br.unb.cic.flang

import MErr._
import MErr.eh._

import Declarations._
import Substitution._

object Interpreter {

  def eval(expr: Expr, declarations: List[FDeclaration]): MError[Value] =
    expr match {
      case CInt(v) => pure(IntValue(v))
      case CBool(v) => pureBool(BoolValue(v))
      case Add(lhs, rhs) =>
        for {
          l <- eval(lhs, declarations)
          r <- eval(rhs, declarations)
          res <- (l, r) match {
            case (IntValue(lv), IntValue(rv)) => pure(IntValue(lv + rv))
            case _ => raiseError("Add requires both arguments to be integers")
          }
        } yield res
      case Mul(lhs, rhs) =>
        for {
          l <- eval(lhs, declarations)
          r <- eval(rhs, declarations)
          res <- (l,r) match{
            case ((IntValue(lv), IntValue(rv))) => pure(IntValue(lv * rv))
            case _ => raiseError("Add requires both arguments to be integers")
          }
        }yield res 
      case And(lhs, rhs) => 
        for{
          l <- eval(lhs, declarations)
          r<- eval(rhs, declarations)
          res<- (l,r) match{
            case ((BoolValue(lv), BoolValue(rv)))=> pureBool(BoolValue(lv && rv))
            case _ => raiseError("Add requires both arguments to be booleans")
          }
        }yield res
      case Or(lhs, rhs) => 
        for{
          l <- eval(lhs, declarations)
          r <- eval(rhs, declarations)
          res <- (l,r) match{
            case ((BoolValue(lv), BoolValue(rv)))=> pureBool(BoolValue(lv || rv))
            case _ => raiseError("Add requires both arguments to be booleans")
          }
        }yield res
      case Not(expr) => 
        for{
          v <- eval(expr, declarations)
          res <- v match{
            case (BoolValue(v)) => pureBool(BoolValue(!v))
            case _ => raiseError("Add requires both arguments to be booleans")
          }
        }yield res
      case Id(name) =>
      for {
        state <- get()
        value <- lookupVar(name, state)
      }yield value
      case App(name, arg) => {
        for {
          fdecl <- lookup(name, declarations) 
          value <- eval(arg, declarations) 
          state <- get() 
          _ <- put(declareVar(fdecl.arg, value, state)) 
          result <- eval(fdecl.body, declarations) 
        } yield result
      }
      case IfThenElse(cond, ifExpr, elseExpr) => for{
        c <- eval(cond,declarations)
        res <- c match{
          case BoolValue(value) => if(value) eval(ifExpr, declarations) else eval(elseExpr, declarations)
          case IntValue(value) => raiseError("Requires a boolean value")
        }
    }yield res
  }

  def runEval(expr: Expr, declarations: List[FDeclaration]): Either[String, Any] = {
    eval(expr, declarations).value.runA(Nil).value match {
      case Right(IntValue(value)) => Right(value)
      case Right(BoolValue(value)) => Right(value)
      case Right(_) => Left("Unexpected value type")
      case Left(error) => Left(error)
    }
}

}
