package br.unb.cic.flang

import cats.MonadError
import cats.instances.either._
import cats.data._

package object MErr {
  
  sealed trait Value
  case class IntValue(value: Integer) extends Value
  case class BoolValue(value: Boolean) extends Value

  type S = List[(String,Value)]
  type M[A]=State[S,A]
  type MError[V] = EitherT[M,String, V]
  

  val eh = MonadError[MError, String]

    /**def ValuetoInt(value: Value): MError[Integer] = value match {
    case IntValue(v) => eh.pure(v)
    case _ => eh.raiseError("Expected an IntValue")
  }

  def ValuetoBoolean(value: Value): MError[Boolean] = value match {
    case BoolValue(v) => pureBool(v)
    case _ => eh.raiseError("Expected a BoolValue")
  }

  def intToValue(value: Integer): Value = IntValue(value)
  def boolToValue(value: Boolean): Value = BoolValue(value)
*/
  def assertError[A](m: MError[A]) : Boolean = {
    val stateresult : M[Either[String,A]]= m.value
    val (state,result) = stateresult.run(List.empty[(String, Value)]).value //tentar implementar com runA,igual a runEval
    result match{
      case Left(value) => true
      case Right(value) => false
    }
  }
  def pureBool[A](a: A): MError[A] = EitherT.right(State.pure(a))

  def bind[A, B](m: MError[A])(f: A => MError[B]): MError[B] = m.flatMap(f)

  def runState[A](s: MError[A]): S => (Either[String, A], S) = 
  initialState => s.value.run(initialState).value.swap

  def put(s: S): MError[Unit] = EitherT.right(State.set(s))
  
  def get[A](): MError[S] = EitherT.right(State.get)

  def declareVar(name: String, value: Value, state: S): S =
    (name, value) :: state

  def lookupVar(name: String, state: S): MError[Value] = {
    state match {
      case List() => eh.raiseError(s"Variable $name not found")
      case (n, v) :: tail if n == name => eh.pure(v)
      case _ :: tail => lookupVar(name, tail)
    }
  }

}