package br.unb.cic.flang

object Substitution {

  def substitute(what: Expr, name: String, in: Expr): Expr = in match {
    case c @ CInt(v) => c
    case c @ CBool(v) => c
    case Id(n) if n == name => what
    case id @ Id(_) => id
    case Add(lhs, rhs) => Add(substitute(what, name, lhs), substitute(what, name, rhs))
    case Mul(lhs, rhs) => Mul(substitute(what, name, lhs), substitute(what, name, rhs))
    case FApp(n, arg) => FApp(n, substitute(what, n, arg))
    case IfThenElse(cond, thenExpr, elseExpr) => IfThenElse(substitute(what, name, cond), substitute(what, name, thenExpr), substitute(what, name, elseExpr))
    case And(lhs, rhs) => And(substitute(what, name, lhs), substitute(what, name, rhs))
    case Or(lhs, rhs) => Or(substitute(what, name, lhs), substitute(what, name, rhs))
    case Not(expr) => Not(substitute(what, name, expr))
  }

}
