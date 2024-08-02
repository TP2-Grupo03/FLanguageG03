file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Interpreter.scala
### java.nio.file.InvalidPathException: Illegal char <:> at index 4: file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.13.14
Classpath:
<WORKSPACE>\.bloop\root\bloop-bsp-clients-classes\classes-Metals-iI2EgIbMTyi8-EgGFzaQHQ== [exists ], <HOME>\AppData\Local\bloop\cache\semanticdb\com.sourcegraph.semanticdb-javac.0.10.0\semanticdb-javac-0.10.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.14\scala-library-2.13.14.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scalactic\scalactic_2.13\3.2.19\scalactic_2.13-3.2.19.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-core_2.13\2.12.0\cats-core_2.13-2.12.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-reflect\2.13.14\scala-reflect-2.13.14.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-kernel_2.13\2.12.0\cats-kernel_2.13-2.12.0.jar [exists ]
Options:
-Yrangepos -Xplugin-require:semanticdb


action parameters:
offset: 1549
uri: file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Interpreter.scala
text:
```scala
package br.unb.cic.flang

import MErr._
import MErr.eh._

import Declarations._
import Substitution._

object Interpreter {

  def eval(expr: Expr, declarations: List[FDeclaration]): MError[Value] =
    expr match {
      case CInt(v) => pure(IntValue(v))
      case Bool(v) => pureBool(BoolValue(v))
      case Add(lhs, rhs) =>
        for {
          l <- eval(lhs, declarations)
          r <- eval(rhs, declarations)
          res <- (l, r) match {
            case (IntValue(lv), IntValue(rv)) => pure(IntValue(lv + rv))
            case _ => raiseError("Add requires both arguments to be integers")
          }
        } yield res
      case Mul(lhs, rhs) =>
        for {
          l <- eval(lhs, declarations)
          r <- eval(rhs, declarations)
          res <- (l,r) match{
            case ((IntValue(lv), IntValue(rv))) => pure(IntValue(lv * rv))
            case _ => raiseError("Add requires both arguments to be integers")
          }
        }yield res 
      case Id(name) =>
      for {
        state <- get()
        value <- lookupVar(name, state)
      }yield value
      case App(name, arg) => {
        for {
          fdecl <- lookup(name, declarations) 
          value <- eval(arg, declarations) 
          state <- get() 
          _ <- put(declareVar(fdecl.arg, value, state)) 
          result <- eval(fdecl.body, declarations) 
        } yield result
      }
      case IfThenElse(cond, ifExpr, elseExpr) => for{
        c <- eval(cond,declarations)
        res <- c m@@
    }yield c
  }

  def runEval(expr: Expr, declarations: List[FDeclaration]): Either[String, Any] = {
  eval(expr, declarations).value.runA(Nil).value match {
    case Right(IntValue(value)) => Right(value)
    case Right(BoolValue(value)) => Right(value)
    case Right(_) => Left("Unexpected value type")
    case Left(error) => Left(error)
  }
}

}

```



#### Error stacktrace:

```
java.base/sun.nio.fs.WindowsPathParser.normalize(WindowsPathParser.java:182)
	java.base/sun.nio.fs.WindowsPathParser.parse(WindowsPathParser.java:153)
	java.base/sun.nio.fs.WindowsPathParser.parse(WindowsPathParser.java:77)
	java.base/sun.nio.fs.WindowsPath.parse(WindowsPath.java:92)
	java.base/sun.nio.fs.WindowsFileSystem.getPath(WindowsFileSystem.java:232)
	java.base/java.nio.file.Path.of(Path.java:147)
	java.base/java.nio.file.Paths.get(Paths.java:69)
	scala.meta.io.AbsolutePath$.apply(AbsolutePath.scala:58)
	scala.meta.internal.metals.MetalsSymbolSearch.$anonfun$definitionSourceToplevels$2(MetalsSymbolSearch.scala:70)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.metals.MetalsSymbolSearch.definitionSourceToplevels(MetalsSymbolSearch.scala:69)
	scala.meta.internal.pc.completions.MatchCaseCompletions.scala$meta$internal$pc$completions$MatchCaseCompletions$$sortSubclasses(MatchCaseCompletions.scala:368)
	scala.meta.internal.pc.completions.MatchCaseCompletions$MatchKeywordCompletion.contribute(MatchCaseCompletions.scala:305)
	scala.meta.internal.pc.CompletionProvider.filterInteresting(CompletionProvider.scala:405)
	scala.meta.internal.pc.CompletionProvider.safeCompletionsAt(CompletionProvider.scala:569)
	scala.meta.internal.pc.CompletionProvider.completions(CompletionProvider.scala:59)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$complete$1(ScalaPresentationCompiler.scala:214)
```
#### Short summary: 

java.nio.file.InvalidPathException: Illegal char <:> at index 4: file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Monad.scala