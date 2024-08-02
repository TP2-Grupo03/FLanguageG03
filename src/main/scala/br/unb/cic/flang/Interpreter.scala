package br.unb.cic.flang

import MErr._
import MErr.eh._

import Declarations._
import Substitution._

object Interpreter {
// eval utiliza encapsulamento e desencapsulamento de ValueType, refatoração mudando o retorno para MError[Any]
// pode ser interessante ( a testar se funciona)
  def eval(expr: Expr, declarations: List[FDeclaration]): MError[ValueType] = expr match {
    // casos que usam Integer
    case CInt(v) => pure(intToValue(v))
    case Add(lhs, rhs) =>
      for {
        l <- eval(lhs, declarations)
        r <- eval(rhs, declarations)
        lv <- ValuetoInt(l)
        rv <- ValuetoInt(r)
        result <- pure(intToValue(lv + rv))
      } yield result
    case Mul(lhs, rhs) =>
      for {
        l <- eval(lhs, declarations)
        r <- eval(rhs, declarations)
        lv <- ValuetoInt(l)
        rv <- ValuetoInt(r)
        result <- pure(intToValue(lv * rv))
      } yield result
    
    
// casos que usam Boolean
    case CBool(v) => pure(boolToValue(v))
    case And(lhs, rhs) =>
      for {
        l <- eval(lhs, declarations)
        r <- eval(rhs, declarations)
        lv <- ValuetoBoolean(l)
        rv <- ValuetoBoolean(r)
        result <- pure(boolToValue(lv && rv))
      } yield result
    case Or(lhs, rhs) =>
      for {
        l <- eval(lhs, declarations)
        r <- eval(rhs, declarations)
        lv <- ValuetoBoolean(l)
        rv <- ValuetoBoolean(r)
        result <- pure(boolToValue(lv || rv))
      } yield result
    case Not(expr) =>
      for {
        v <- eval(expr, declarations)
        b <- ValuetoBoolean(v)
        result <- pure(boolToValue(!b))
      } yield result
    case IfThenElse(cond, tExpr, fExpr) =>
      for {
        condVal <- eval(cond, declarations)
        condBool <- ValuetoBoolean(condVal)
        result <- if (condBool) eval(tExpr, declarations) else eval(fExpr, declarations)
      } yield result
    // casos que lidam diretamente com ValueType
    case Id(name) =>
      for {
        state <- get()
        value <- lookupVar(name, state)
      } yield value
    case App(name, arg) =>
      for {
        fdecl <- lookup(name, declarations)
        value <- eval(arg, declarations)
        state <- get()
        _ <- put(declareVar(fdecl.arg, value, state))
        result <- eval(fdecl.body, declarations)
      } yield result

  }
// função que desencapsula o MonadTransformer,deve ser chamada em testes ao invés de eval
  def runEval(expr: Expr, declarations: List[FDeclaration]): Either[String, Any] = {
    eval(expr, declarations).value.runA(Nil).value match {
      case Right(IntValue(value)) => Right(value)
      case Right(BoolValue(value)) => Right(value)
      case Right(_) => Left("Unexpected value type")
      case Left(error) => Left(error)
    }
  }
}
