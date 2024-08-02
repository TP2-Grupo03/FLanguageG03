jar:file:///C:/Users/marcu/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.14/scala-library-2.13.14-sources.jar!/scala/AnyRef.scala
### java.lang.RuntimeException: unsupported type Any {
  def $init$(): Unit
  def equals(that: Any): Boolean
  def hashCode: Int
  def toString: String
  def synchronized[T](body: => T): T
  final def eq(that: AnyRef): Boolean
  final def ne(that: AnyRef): Boolean
  final def ==(that: Any): Boolean
  protected def clone(): AnyRef
  protected def finalize(): Unit
  final def notify(): Unit
  final def notifyAll(): Unit
  final def wait(): Unit
  final def wait(timeout: Long,nanos: Int): Unit
  final def wait(timeout: Long): Unit
}: ClassInfoType(List(TypeRef(ThisType(scala), scala.Any, List())), Scope(TermName("$init$"), TermName("equals"), TermName("hashCode"), TermName("toString"), TermName("synchronized"), TermName("eq"), TermName("ne"), TermName("$eq$eq"), TermName("clone"), TermName("finalize"), TermName("notify"), TermName("notifyAll"), TermName("wait"), TermName("wait"), TermName("wait")), TypeName("AnyRef"))

occurred in the presentation compiler.

presentation compiler configuration:
Scala version: 2.12.19
Classpath:
<HOME>\AppData\Local\Coursier\cache\v1\https\repo1.maven.org\maven2\org\scala-lang\scala-library\2.12.19\scala-library-2.12.19.jar [exists ]
Options:



action parameters:
uri: jar:file:///C:/Users/marcu/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.14/scala-library-2.13.14-sources.jar!/scala/AnyRef.scala
text:
```scala
/*
 * Scala (https://www.scala-lang.org)
 *
 * Copyright EPFL and Lightbend, Inc.
 *
 * Licensed under Apache License 2.0
 * (http://www.apache.org/licenses/LICENSE-2.0).
 *
 * See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.
 */

package scala

/** Class `AnyRef` is the root class of all ''reference types''.
 *  All types except the value types descend from this class.
 *  @template
 */
trait AnyRef extends Any {

  /** The equality method for reference types.  Default implementation delegates to `eq`.
   *
   *  See also `equals` in [[scala.Any]].
   *
   *  @param  that    the object to compare against this object for equality.
   *  @return         `true` if the receiver object is equivalent to the argument; `false` otherwise.
   */
  def equals(that: Any): Boolean = this eq that

  /** The hashCode method for reference types.  See hashCode in [[scala.Any]].
   *
   *  @return   the hash code value for this object.
   */
  def hashCode: Int = sys.error("hashCode")

  /** Creates a String representation of this object.  The default
   *  representation is platform dependent.  On the java platform it
   *  is the concatenation of the class name, "@", and the object's
   *  hashcode in hexadecimal.
   *
   *  @return     a String representation of the object.
   */
  def toString: String = sys.error("toString")

  /** Executes the code in `body` with an exclusive lock on `this`.
   *
   *  @param    body    the code to execute
   *  @return           the result of `body`
   */
  def synchronized[T](body: => T): T = sys.error("synchronized")

  /** Tests whether the argument (`that`) is a reference to the receiver object (`this`).
   *
   *  The `eq` method implements an [[https://en.wikipedia.org/wiki/Equivalence_relation equivalence relation]] on
   *  non-null instances of `AnyRef`, and has three additional properties:
   *
   *   - It is consistent: for any non-null instances `x` and `y` of type `AnyRef`, multiple invocations of
   *     `x.eq(y)` consistently returns `true` or consistently returns `false`.
   *   - For any non-null instance `x` of type `AnyRef`, `x.eq(null)` and `null.eq(x)` returns `false`.
   *   - `null.eq(null)` returns `true`.
   *
   *  When overriding the `equals` or `hashCode` methods, it is important to ensure that their behavior is
   *  consistent with reference equality.  Therefore, if two objects are references to each other (`o1 eq o2`), they
   *  should be equal to each other (`o1 == o2`) and they should hash to the same value (`o1.hashCode == o2.hashCode`).
   *
   *  @param  that    the object to compare against this object for reference equality.
   *  @return         `true` if the argument is a reference to the receiver object; `false` otherwise.
   */
  final def eq(that: AnyRef): Boolean = sys.error("eq")

  /** Equivalent to `!(this eq that)`.
   *
   *  @param  that    the object to compare against this object for reference equality.
   *  @return         `true` if the argument is not a reference to the receiver object; `false` otherwise.
   */
  final def ne(that: AnyRef): Boolean = !(this eq that)

  /** The expression `x == that` is equivalent to `if (x eq null) that eq null else x.equals(that)`.
   *
   *  @param    that  the object to compare against this object for equality.
   *  @return         `true` if the receiver object is equivalent to the argument; `false` otherwise.
   */
  final def ==(that: Any): Boolean =
    if (this eq null) that.asInstanceOf[AnyRef] eq null
    else this equals that

  /** Create a copy of the receiver object.
   *
   *  The default implementation of the `clone` method is platform dependent.
   *
   *  @note   not specified by SLS as a member of AnyRef
   *  @return a copy of the receiver object.
   */
  protected def clone(): AnyRef

  /** Called by the garbage collector on the receiver object when there
   *  are no more references to the object.
   *
   *  The details of when and if the `finalize` method is invoked, as
   *  well as the interaction between `finalize` and non-local returns
   *  and exceptions, are all platform dependent.
   *
   *  @note   not specified by SLS as a member of AnyRef
   */
  protected def finalize(): Unit

  /** Wakes up a single thread that is waiting on the receiver object's monitor.
   *
   *  @note   not specified by SLS as a member of AnyRef
   */
  final def notify(): Unit

  /** Wakes up all threads that are waiting on the receiver object's monitor.
   *
   *  @note   not specified by SLS as a member of AnyRef
   */
  final def notifyAll(): Unit

  /** See [[https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#wait--]].
   *
   *  @note   not specified by SLS as a member of AnyRef
   */
  final def wait (): Unit

  /** See [[https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#wait-long-int-]]
   *
   * @param timeout the maximum time to wait in milliseconds.
   * @param nanos   additional time, in nanoseconds range 0-999999.
   * @note not specified by SLS as a member of AnyRef
   */
  final def wait (timeout: Long, nanos: Int): Unit

  /** See [[https://docs.oracle.com/javase/8/docs/api/java/lang/Object.html#wait-long-]].
   *
   * @param timeout the maximum time to wait in milliseconds.
   * @note not specified by SLS as a member of AnyRef
   */
  final def wait (timeout: Long): Unit
}

```



#### Error stacktrace:

```
scala.sys.package$.error(package.scala:30)
	scala.meta.internal.semanticdb.scalac.TypeOps$XtensionGTypeSType.loop$1(TypeOps.scala:74)
	scala.meta.internal.semanticdb.scalac.TypeOps$XtensionGTypeSType.toSemanticTpe(TypeOps.scala:76)
	scala.meta.internal.semanticdb.scalac.TypeOps$XtensionGTypeSType.loop$2(TypeOps.scala:104)
	scala.meta.internal.semanticdb.scalac.TypeOps$XtensionGTypeSType.toSemanticSig(TypeOps.scala:127)
	scala.meta.internal.semanticdb.scalac.SymbolInformationOps$XtensionGSymbolMSymbolInformation.sig(SymbolInformationOps.scala:116)
	scala.meta.internal.semanticdb.scalac.SymbolInformationOps$XtensionGSymbolMSymbolInformation.toSymbolInformation(SymbolInformationOps.scala:196)
	scala.meta.internal.semanticdb.scalac.TextDocumentOps$XtensionCompilationUnitDocument$traverser$3$.saveSymbol$1(TextDocumentOps.scala:194)
	scala.meta.internal.semanticdb.scalac.TextDocumentOps$XtensionCompilationUnitDocument$traverser$3$.trySymbolDefinition(TextDocumentOps.scala:196)
	scala.meta.internal.semanticdb.scalac.TextDocumentOps$XtensionCompilationUnitDocument$traverser$3$.tryFindMtree(TextDocumentOps.scala:302)
	scala.meta.internal.semanticdb.scalac.TextDocumentOps$XtensionCompilationUnitDocument$traverser$3$.traverse(TextDocumentOps.scala:590)
	scala.meta.internal.semanticdb.scalac.TextDocumentOps$XtensionCompilationUnitDocument$traverser$3$.traverse(TextDocumentOps.scala:183)
	scala.reflect.api.Trees$Traverser.$anonfun$traverseStats$2(Trees.scala:2506)
	scala.reflect.api.Trees$Traverser.atOwner(Trees.scala:2515)
	scala.reflect.api.Trees$Traverser.$anonfun$traverseStats$1(Trees.scala:2506)
	scala.reflect.api.Trees$Traverser.traverseStats(Trees.scala:2505)
	scala.reflect.internal.Trees.itraverse(Trees.scala:1390)
	scala.reflect.internal.Trees.itraverse$(Trees.scala:1264)
	scala.reflect.internal.SymbolTable.itraverse(SymbolTable.scala:28)
	scala.reflect.internal.SymbolTable.itraverse(SymbolTable.scala:28)
	scala.reflect.api.Trees$Traverser.traverse(Trees.scala:2483)
	scala.meta.internal.semanticdb.scalac.TextDocumentOps$XtensionCompilationUnitDocument$traverser$3$.traverse(TextDocumentOps.scala:656)
	scala.meta.internal.semanticdb.scalac.TextDocumentOps$XtensionCompilationUnitDocument.toTextDocument(TextDocumentOps.scala:659)
	scala.meta.internal.pc.SemanticdbTextDocumentProvider.textDocument(SemanticdbTextDocumentProvider.scala:54)
	scala.meta.internal.pc.ScalaPresentationCompiler.$anonfun$semanticdbTextDocument$1(ScalaPresentationCompiler.scala:469)
```
#### Short summary: 

java.lang.RuntimeException: unsupported type Any {
  def $init$(): Unit
  def equals(that: Any): Boolean
  def hashCode: Int
  def toString: String
  def synchronized[T](body: => T): T
  final def eq(that: AnyRef): Boolean
  final def ne(that: AnyRef): Boolean
  final def ==(that: Any): Boolean
  protected def clone(): AnyRef
  protected def finalize(): Unit
  final def notify(): Unit
  final def notifyAll(): Unit
  final def wait(): Unit
  final def wait(timeout: Long,nanos: Int): Unit
  final def wait(timeout: Long): Unit
}: ClassInfoType(List(TypeRef(ThisType(scala), scala.Any, List())), Scope(TermName("$init$"), TermName("equals"), TermName("hashCode"), TermName("toString"), TermName("synchronized"), TermName("eq"), TermName("ne"), TermName("$eq$eq"), TermName("clone"), TermName("finalize"), TermName("notify"), TermName("notifyAll"), TermName("wait"), TermName("wait"), TermName("wait")), TypeName("AnyRef"))