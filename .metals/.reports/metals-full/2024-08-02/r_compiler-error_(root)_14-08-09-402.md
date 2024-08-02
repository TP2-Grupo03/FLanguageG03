file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
### scala.reflect.internal.FatalError: 
  ThisType(type M) for sym which is not a class
     while compiling: file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
        during phase: globalPhase=<no phase>, enteringPhase=parser
     library version: version 2.13.14
    compiler version: version 2.13.14
  reconstructed args: -classpath <WORKSPACE>\.bloop\root\bloop-bsp-clients-classes\classes-Metals-y8m9TNWkRgm1i53lehwaiw==;<HOME>\AppData\Local\bloop\cache\semanticdb\com.sourcegraph.semanticdb-javac.0.10.0\semanticdb-javac-0.10.0.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.14\scala-library-2.13.14.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scalactic\scalactic_2.13\3.2.19\scalactic_2.13-3.2.19.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-core_2.13\2.12.0\cats-core_2.13-2.12.0.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-reflect\2.13.14\scala-reflect-2.13.14.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-kernel_2.13\2.12.0\cats-kernel_2.13-2.12.0.jar -Xplugin-require:semanticdb -Yrangepos -Ymacro-expand:discard -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: ApplyToImplicitArgs(method catsDataMonadForIndexedStateT)
       tree position: line 29 of file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
            tree tpe: cats.Monad[[δ$9$]cats.data.IndexedStateT[[+A]cats.Eval[A],br.unb.cic.flang.MErr.package.S,br.unb.cic.flang.MErr.package.S,δ$9$]]
              symbol: implicit method catsDataMonadForIndexedStateT in class IndexedStateTInstances2
   symbol definition: implicit def catsDataMonadForIndexedStateT[F[_], S](implicit F0: cats.Monad[F]): cats.Monad[[δ$9$]cats.data.IndexedStateT[F,S,S,δ$9$]] (a MethodSymbol)
      symbol package: cats.data
       symbol owners: method catsDataMonadForIndexedStateT -> class IndexedStateTInstances2
           call site: <none> in <none>

== Source file context for tree position ==

    26       case Right(value) => false
    27     }
    28   }
    29   def pureBool(a: _CURSOR_A): MError[Value] = EitherT.right(State.pure(BoolValue(a)))
    30 
    31   def bind[A, B](m: MError[A])(f: A => MError[B]): MError[B] = m.flatMap(f)
    32 

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.13.14
Classpath:
<WORKSPACE>\.bloop\root\bloop-bsp-clients-classes\classes-Metals-y8m9TNWkRgm1i53lehwaiw== [exists ], <HOME>\AppData\Local\bloop\cache\semanticdb\com.sourcegraph.semanticdb-javac.0.10.0\semanticdb-javac-0.10.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.14\scala-library-2.13.14.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scalactic\scalactic_2.13\3.2.19\scalactic_2.13-3.2.19.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-core_2.13\2.12.0\cats-core_2.13-2.12.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-reflect\2.13.14\scala-reflect-2.13.14.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-kernel_2.13\2.12.0\cats-kernel_2.13-2.12.0.jar [exists ]
Options:
-Yrangepos -Xplugin-require:semanticdb


action parameters:
offset: 827
uri: file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
text:
```scala
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
  //def pureMError[A](a :A): MError[A] = EitherT.right(State.pure[S,A](a))
  
  def assertError[A](m: MError[A]) : Boolean = {
    val stateresult : M[Either[String,A]]= m.value
    val (state,result) = stateresult.run(List.empty[(String, Value)]).value //tentar implementar com runA,igual a runEval
    result match{
      case Left(value) => true
      case Right(value) => false
    }
  }
  def pureBool(a: @@A): MError[Value] = EitherT.right(State.pure(BoolValue(a)))

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
```



#### Error stacktrace:

```
scala.reflect.internal.Reporting.abort(Reporting.scala:70)
	scala.reflect.internal.Reporting.abort$(Reporting.scala:66)
	scala.reflect.internal.SymbolTable.abort(SymbolTable.scala:28)
	scala.reflect.internal.Types$ThisType.<init>(Types.scala:1394)
	scala.reflect.internal.Types$UniqueThisType.<init>(Types.scala:1414)
	scala.reflect.internal.Types$ThisType$.apply(Types.scala:1418)
	scala.meta.internal.pc.AutoImportsProvider$$anonfun$autoImports$3.applyOrElse(AutoImportsProvider.scala:74)
	scala.meta.internal.pc.AutoImportsProvider$$anonfun$autoImports$3.applyOrElse(AutoImportsProvider.scala:60)
	scala.collection.immutable.List.collect(List.scala:268)
	scala.meta.internal.pc.AutoImportsProvider.autoImports(AutoImportsProvider.scala:60)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$autoImports$1(ScalaPresentationCompiler.scala:306)
```
#### Short summary: 

scala.reflect.internal.FatalError: 
  ThisType(type M) for sym which is not a class
     while compiling: file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
        during phase: globalPhase=<no phase>, enteringPhase=parser
     library version: version 2.13.14
    compiler version: version 2.13.14
  reconstructed args: -classpath <WORKSPACE>\.bloop\root\bloop-bsp-clients-classes\classes-Metals-y8m9TNWkRgm1i53lehwaiw==;<HOME>\AppData\Local\bloop\cache\semanticdb\com.sourcegraph.semanticdb-javac.0.10.0\semanticdb-javac-0.10.0.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.14\scala-library-2.13.14.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scalactic\scalactic_2.13\3.2.19\scalactic_2.13-3.2.19.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-core_2.13\2.12.0\cats-core_2.13-2.12.0.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-reflect\2.13.14\scala-reflect-2.13.14.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-kernel_2.13\2.12.0\cats-kernel_2.13-2.12.0.jar -Xplugin-require:semanticdb -Yrangepos -Ymacro-expand:discard -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: ApplyToImplicitArgs(method catsDataMonadForIndexedStateT)
       tree position: line 29 of file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
            tree tpe: cats.Monad[[δ$9$]cats.data.IndexedStateT[[+A]cats.Eval[A],br.unb.cic.flang.MErr.package.S,br.unb.cic.flang.MErr.package.S,δ$9$]]
              symbol: implicit method catsDataMonadForIndexedStateT in class IndexedStateTInstances2
   symbol definition: implicit def catsDataMonadForIndexedStateT[F[_], S](implicit F0: cats.Monad[F]): cats.Monad[[δ$9$]cats.data.IndexedStateT[F,S,S,δ$9$]] (a MethodSymbol)
      symbol package: cats.data
       symbol owners: method catsDataMonadForIndexedStateT -> class IndexedStateTInstances2
           call site: <none> in <none>

== Source file context for tree position ==

    26       case Right(value) => false
    27     }
    28   }
    29   def pureBool(a: _CURSOR_A): MError[Value] = EitherT.right(State.pure(BoolValue(a)))
    30 
    31   def bind[A, B](m: MError[A])(f: A => MError[B]): MError[B] = m.flatMap(f)
    32 