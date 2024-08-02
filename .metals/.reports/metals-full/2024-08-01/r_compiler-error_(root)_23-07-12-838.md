file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Interpreter.scala
### java.lang.NullPointerException: Cannot invoke "scala.reflect.internal.Symbols$Symbol.isModule()" because the return value of "scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.methodSym()" is null

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.13.14
Classpath:
<WORKSPACE>\.bloop\root\bloop-bsp-clients-classes\classes-Metals-YkrSNnTBQQS0K3OTj8hpGQ== [exists ], <HOME>\AppData\Local\bloop\cache\semanticdb\com.sourcegraph.semanticdb-javac.0.10.0\semanticdb-javac-0.10.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.13.14\scala-library-2.13.14.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scalactic\scalactic_2.13\3.2.19\scalactic_2.13-3.2.19.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-core_2.13\2.12.0\cats-core_2.13-2.12.0.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-reflect\2.13.14\scala-reflect-2.13.14.jar [exists ], <HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\typelevel\cats-kernel_2.13\2.12.0\cats-kernel_2.13-2.12.0.jar [exists ]
Options:
-Yrangepos -Xplugin-require:semanticdb


action parameters:
offset: 1645
uri: file:///C:/Users/marcu/Desktop/Emanuel/UnB/Materias/2024.1/TP2/FLanguageG03/src/main/scala/br/unb/cic/flang/Interpreter.scala
text:
```scala
package br.unb.cic.flang

import MErr._
import MErr.eh._

import Declarations._
import Substitution._

object Interpreter {

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
          fdecl <- lookup(name, declarations) 
          value <- eval(arg, declarations) 
          state <- get() 
          _ <- put(declareVar(fdecl.arg, value, state)) 
          result <- eval(fdecl.body, declarations) 
        } yield result
      }
      case Bool(v) => pure(v)
      case And(lhs, rhs) =>for {
          l <- eval(lhs, declarations)
          r <- eval(rhs, declarations)
        }yield (r && l)
      case Or(lhs, rhs) => for{
          l <- eval(lhs, declarations)
          r <- eval(rhs, declarations)
        }yield (r && l)
      //case Not(expr) => {
      //  for{
      //    r<- eval(expr, declarations)
      //  }yield ()
      //}
      case IfThenElse(cond, ifExpr, elseExpr) => for{
        c <- eval(cond,declarations)
        res <- if (c is true){
          eval(ifExpr, declarations)
        }else{ eval(elseExpr, declarations)}
      }yield r@@
    }
  def runEval(expr: Expr, declarations: List[FDeclaration]): Either[String, Integer] = {
    eval(expr, declarations).value.runA(Nil).value
  }
}
```



#### Error stacktrace:

```
scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.methodsParams$lzycompute(ArgCompletions.scala:30)
	scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.methodsParams(ArgCompletions.scala:29)
	scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.allParams$lzycompute(ArgCompletions.scala:81)
	scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.allParams(ArgCompletions.scala:81)
	scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.params$lzycompute(ArgCompletions.scala:83)
	scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.params(ArgCompletions.scala:82)
	scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.isParamName$lzycompute(ArgCompletions.scala:90)
	scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.isParamName(ArgCompletions.scala:90)
	scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.isName(ArgCompletions.scala:96)
	scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.compare(ArgCompletions.scala:99)
	scala.meta.internal.pc.completions.Completions$$anon$1.compare(Completions.scala:236)
	scala.meta.internal.pc.completions.Completions$$anon$1.compare(Completions.scala:192)
	java.base/java.util.TimSort.countRunAndMakeAscending(TimSort.java:355)
	java.base/java.util.TimSort.sort(TimSort.java:220)
	java.base/java.util.Arrays.sort(Arrays.java:1233)
	scala.collection.SeqOps.sorted(Seq.scala:728)
	scala.collection.SeqOps.sorted$(Seq.scala:719)
	scala.collection.immutable.List.scala$collection$immutable$StrictOptimizedSeqOps$$super$sorted(List.scala:79)
	scala.collection.immutable.StrictOptimizedSeqOps.sorted(StrictOptimizedSeqOps.scala:75)
	scala.collection.immutable.StrictOptimizedSeqOps.sorted$(StrictOptimizedSeqOps.scala:75)
	scala.collection.immutable.List.sorted(List.scala:79)
	scala.meta.internal.pc.CompletionProvider.completions(CompletionProvider.scala:73)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$complete$1(ScalaPresentationCompiler.scala:214)
```
#### Short summary: 

java.lang.NullPointerException: Cannot invoke "scala.reflect.internal.Symbols$Symbol.isModule()" because the return value of "scala.meta.internal.pc.completions.ArgCompletions$ArgCompletion.methodSym()" is null