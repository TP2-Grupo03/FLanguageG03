package br.unb.cic.flang

import cats.MonadError
import cats.instances.either._
import cats.data._

package object MErr {
  
  sealed trait ValueType  // aqui criamos um sealed traid Valuetype que aceita somente Integer e Boolean
  case class BoolValue(value: Boolean) extends ValueType
  case class IntValue(value: Integer) extends ValueType
  //funções referentes a encapsulamento e desencapsulamento de Valuetype e Integer e Boolean
  def intToValue(value: Integer): ValueType = IntValue(value)
  def boolToValue(value: Boolean): ValueType = BoolValue(value)

  def ValuetoInt(value: ValueType): MError[Integer] = value match {
    case IntValue(v) => eh.pure(v)
    case _ => eh.raiseError(s"Expected an integer, but got $value")
  }

  def ValuetoBoolean(value: ValueType): MError[Boolean] = value match {
    case BoolValue(v) => eh.pure(v)
    case _ => eh.raiseError(s"Expected a boolean, but got $value")
  }

// implementação da MonadTrasnformer EitherT com State,necessária para lidar com variáveis globais(State) e exceções(EitherT)
  type S = List[(String, ValueType)]
  type M[A] = State[S, A]
  type MError[V] = EitherT[M, String, V]
  val eh = MonadError[MError, String]

  def assertError[A](m: MError[A]): Boolean = {
    val stateresult: M[Either[String, A]] = m.value
    val (state, result) = stateresult.run(List.empty[(String, ValueType)]).value
    result match {
      case Left(_) => true
      case Right(_) => false
    }
  }

  def pureInt[A](a: A) = ??? //usamos o método eh.pure que próvem da MonadError
  def pureBool[A](a: A): MError[A] = EitherT.right(State.pure(a)) // método eh.pure não aceita Boolean
  def bind[A, B](m: MError[A])(f: A => MError[B]): MError[B] = m.flatMap(f)

  def runState[A](s: MError[A]): S => (Either[String, A], S) =
    initialState => s.value.run(initialState).value.swap

  def put(s: S): MError[Unit] = EitherT.right(State.set(s))
  def get[A](): MError[S] = EitherT.right(State.get)

  def declareVar(name: String, value: ValueType, state: S): S =
    (name, value) :: state

  def lookupVar(name: String, state: S): MError[ValueType] = {
    state match {
      case List() => eh.raiseError(s"Variable $name not found")
      case (n, v) :: tail if n == name => eh.pure(v)
      case _ :: tail => lookupVar(name, tail)
    }
  }
}
