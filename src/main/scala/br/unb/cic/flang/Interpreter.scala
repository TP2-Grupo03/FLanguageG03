package br.unb.cic.flang

import MErr._
import MErr.eh._

import Declarations._


object Interpreter {

  /** This implementation relies on a state monad.
    *
    * Here we replace the substitution function (that needs to traverse the AST
    * twice during interpretation), by a 'global' state that contains the
    * current 'bindings'. The bindings are pairs from names to integers.
    *
    * We only update the state when we are interpreting a function application.
    * This implementation deals with sections 6.1 and 6.2 of the book
    * "Programming Languages: Application and Interpretation". However, here we
    * use a monad state, instead of passing the state explicitly as an agument
    * to the eval function.
    *
    * Sections 6.3 and 6.4 improves this implementation. We will left such an
    * improvements as an exercise.
  */
  def eval(expr: Expr, declarations: List[FDeclaration]): MError[Integer] =
    expr match {
      case CInt(v) => pure(v)
      case Add(lhs, rhs) =>
        bind(eval(lhs, declarations))({ l =>
          bind(eval(rhs, declarations))({ r => pure(l + r) })
        })
      case Mul(lhs, rhs) =>
        bind(eval(lhs, declarations))({ l =>
          bind(eval(rhs, declarations))({ r => pure(l * r) })
        })
      case Id(name) =>
      for {
        state <- get()
        value <- lookupVar(name, state)
      } yield value
      case App(name, arg) => {
        for {
          fdecl <- lookup(name, declarations) // Obter a declaração da função
          value <- eval(arg, declarations) // Avaliar o argumento da função
          state <- get() // Obter o estado atual
          _ <- put(declareVar(fdecl.arg, value, state)) // Atualizar o estado com o novo valor
          result <- eval(fdecl.body, declarations) // Avaliar o corpo da função com o novo estado
        } yield result
      }
    }
  def runEval(expr: Expr, declarations: List[FDeclaration]): Either[String, Integer] = {
    eval(expr, declarations).value.runA(Nil).value
  }
}