package br.unb.cic.flang

import cats.MonadError
import cats.instances.either._
import cats.data._

package object MErr {

  type S = List[(String,Integer)]
  type M[A]=State[S,A]
  type MError[V] = EitherT[M,String, V]

  val eh = MonadError[MError, String]

  def pureMError[A](a :A): MError[A] = EitherT.right(State.pure[S,A](a))

  def assertError[A](m: MError[A]) : Boolean = {
    val stateresult : M[Either[String,A]]= m.value
    val (state,result) = stateresult.run(List.empty[(String, Integer)]).value
    result match{
      case Left(value) => true
      case Right(value) => false
    }
  }
}