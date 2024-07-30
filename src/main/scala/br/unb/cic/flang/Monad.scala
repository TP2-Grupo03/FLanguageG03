package br.unb.cic.flang
import cats.data.State
import cats.implicits._

package object StateMonad {
  type S = List[(String, Integer)]
  type M[A]=State[S,A]

  def pure[A](a: A): M[A] = State.pure(a)

  def bind[A, B](m: M[A])(f: A => M[B]): M[B] = m.flatMap(f)

  def runState[A](s: M[A]): (S => (A, S)) = s.run(_).value.swap

  def put(s: S): M[Unit] = State.set(s)
  def get[A](): M[S] = State.get

  def declareVar(name: String, value: Integer, state: S): S =
    (name, value) :: state

  def lookupVar(name: String, state: S): Integer = state match {
    case List()                      => ???
    case (n, v) :: tail if n == name => v
    case _ :: tail                   => lookupVar(name, tail)
  }

}
