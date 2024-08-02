package br.unb.cic.flang

import cats.MonadError
import cats.instances.either._
import cats.data._

package object MErr {

  type S = List[(String,Integer)]
  type M[A]=State[S,A]
  type MError[V] = EitherT[M,String, V]

  val eh = MonadError[MError, String]

  // def pureMError[A](a :A): MError[A] = EitherT.right(State.pure[S,A](a))

  def assertError[A](m: MError[A]) : Boolean = {
    val stateresult : M[Either[String,A]]= m.value
    val (state,result) = stateresult.run(List.empty[(String, Integer)]).value //tentar implementar com runA,igual a runEval
    result match{
      case Left(value) => true
      case Right(value) => false
    }
  }
  
  def bind[A, B](m: MError[A])(f: A => MError[B]): MError[B] = m.flatMap(f)

  def runState[A](s: MError[A]): S => (Either[String, A], S) = 
  initialState => s.value.run(initialState).value.swap

  def put(s: S): MError[Unit] = EitherT.right(State.set(s))
  
  def get[A](): MError[S] = EitherT.right(State.get)

  def declareVar(name: String, value: Integer, state: S): S =
    (name, value) :: state

def lookupVar(name: String, state: S): MError[Integer] = {
  state match {
    case List() => eh.raiseError(s"Variable $name not found")
    case (n, v) :: tail if n == name => eh.pure(v)
    case _ :: tail => lookupVar(name, tail)
  }
}

}