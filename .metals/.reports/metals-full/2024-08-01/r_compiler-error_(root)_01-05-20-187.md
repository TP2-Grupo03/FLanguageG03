file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
### scala.reflect.internal.FatalError: 
  ThisType(method pured) for sym which is not a class
     while compiling: file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
        during phase: globalPhase=<no phase>, enteringPhase=parser
     library version: version 2.13.14
    compiler version: version 2.13.14
  reconstructed args: -classpath <WORKSPACE>\.bloop\root\bloop-bsp-clients-classes\classes-Metals-5JW_BWNSSg-I974xoqOo-Q==;<HOME>\AppData\Local\bloop\cache\semanticdb\com.sourcegraph.semanticdb-javac.0.10.0\semanticdb-javac-0.10.0.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.14\scala-library-2.13.14.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scalactic\scalactic_2.13\3.2.19\scalactic_2.13-3.2.19.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-core_2.13\2.12.0\cats-core_2.13-2.12.0.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-reflect\2.13.14\scala-reflect-2.13.14.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-kernel_2.13\2.12.0\cats-kernel_2.13-2.12.0.jar -Xplugin-require:semanticdb -Yrangepos -Ymacro-expand:discard -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: TypeTree(trait MonadError)
       tree position: line 12 of file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
            tree tpe: cats.MonadError[br.unb.cic.flang.MErr.package.MError,String]
              symbol: abstract trait MonadError in package cats
   symbol definition: abstract trait MonadError[F[_ <: <?>], E] extends ApplicativeError[F,E] with Monad[F] (a ClassSymbol)
      symbol package: cats
       symbol owners: trait MonadError
           call site: <none> in <none>

== Source file context for tree position ==

     9   type M[A]=State[S,A]
    10   type MError[V] = EitherT[M,String, V]
    11 
    12   val eh = MonadError[MError, String]{
    13       def pured[A](a: A): M[A] = EitherT.pure[M, _CURSOR_A]
    14   }
    15 

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.13.14
Classpath:
<WORKSPACE>\.bloop\root\bloop-bsp-clients-classes\classes-Metals-5JW_BWNSSg-I974xoqOo-Q== [exists ], <HOME>\AppData\Local\bloop\cache\semanticdb\com.sourcegraph.semanticdb-javac.0.10.0\semanticdb-javac-0.10.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.14\scala-library-2.13.14.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scalactic\scalactic_2.13\3.2.19\scalactic_2.13-3.2.19.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-core_2.13\2.12.0\cats-core_2.13-2.12.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-reflect\2.13.14\scala-reflect-2.13.14.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-kernel_2.13\2.12.0\cats-kernel_2.13-2.12.0.jar [exists ]
Options:
-Yrangepos -Xplugin-require:semanticdb


action parameters:
offset: 320
uri: file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
text:
```scala
package br.unb.cic.flang

import cats.MonadError
import cats.instances.either._
import cats.data._

package object MErr {
  type S = List[(String,Integer)]
  type M[A]=State[S,A]
  type MError[V] = EitherT[M,String, V]

  val eh = MonadError[MError, String]{
      def pured[A](a: A): M[A] = EitherT.pure[M, @@A]
  }

  def assertError[A](m: MError[A]) : Boolean = {
    val stateresult : M[Either[String,A]]= m.value
    val (state,result) = stateresult.run(List.empty[(String, Integer)]).value
    result match{
      case Left(value) => true
      case Right(value) => false
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
  ThisType(method pured) for sym which is not a class
     while compiling: file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
        during phase: globalPhase=<no phase>, enteringPhase=parser
     library version: version 2.13.14
    compiler version: version 2.13.14
  reconstructed args: -classpath <WORKSPACE>\.bloop\root\bloop-bsp-clients-classes\classes-Metals-5JW_BWNSSg-I974xoqOo-Q==;<HOME>\AppData\Local\bloop\cache\semanticdb\com.sourcegraph.semanticdb-javac.0.10.0\semanticdb-javac-0.10.0.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.14\scala-library-2.13.14.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scalactic\scalactic_2.13\3.2.19\scalactic_2.13-3.2.19.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-core_2.13\2.12.0\cats-core_2.13-2.12.0.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-reflect\2.13.14\scala-reflect-2.13.14.jar;<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-kernel_2.13\2.12.0\cats-kernel_2.13-2.12.0.jar -Xplugin-require:semanticdb -Yrangepos -Ymacro-expand:discard -Ycache-plugin-class-loader:last-modified -Ypresentation-any-thread

  last tree to typer: TypeTree(trait MonadError)
       tree position: line 12 of file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala
            tree tpe: cats.MonadError[br.unb.cic.flang.MErr.package.MError,String]
              symbol: abstract trait MonadError in package cats
   symbol definition: abstract trait MonadError[F[_ <: <?>], E] extends ApplicativeError[F,E] with Monad[F] (a ClassSymbol)
      symbol package: cats
       symbol owners: trait MonadError
           call site: <none> in <none>

== Source file context for tree position ==

     9   type M[A]=State[S,A]
    10   type MError[V] = EitherT[M,String, V]
    11 
    12   val eh = MonadError[MError, String]{
    13       def pured[A](a: A): M[A] = EitherT.pure[M, _CURSOR_A]
    14   }
    15 