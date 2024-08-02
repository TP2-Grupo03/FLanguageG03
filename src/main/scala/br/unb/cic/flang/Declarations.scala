package br.unb.cic.flang

import MErr._
import MErr.eh._
// import cats.syntax.applicative._       // for pure
// import cats.syntax.applicativeError._  // for raiseError

case class FDeclaration(name: String, arg: String, body: Expr)

object Declarations {
  def lookup(
      name: String,
      declarations: List[FDeclaration]
  ): MError[FDeclaration] = declarations match {
    case List() => raiseError(s"Function $name is not declared")
    case (f@FDeclaration(n, a, b))::_ if n == name => pure(f)
    case _::fs => lookup(name, fs)  
  }

}