package br.unb.cic.flang

import org.scalatest._
import flatspec._
import matchers._

import FLangParser._


class ParserTests extends AnyFlatSpec with should.Matchers {


  "An integer parser" should "parse integers correctly" in {
    parseInput("42") shouldEqual (List(), CInt(42))
  }
  
  "A boolean parser" should "parse booleans correctly" in {
    parseInput("true") shouldEqual (List(), CBool(true))
    parseInput("false") shouldEqual (List(), CBool(false))
  }

  "An addition parser" should "parse addition expressions correctly" in {
    parseInput("5 + 10") shouldEqual (List(), Add(CInt(5), CInt(10)))
  }

  "A multiplication parser" should "parse multiplication expressions correctly" in {
    parseInput("5 * 10")  shouldEqual (List(), Mul(CInt(5), CInt(10)))
  }

  "A identifier parser" should "parse identifiers correctly" in {
    parseInput("inc") shouldEqual (List(), Id("inc"))
  }

  "An if-then-else parser" should "parse if-then-else expressions correctly" in {
    parseInput("if true then 1 else 0") shouldEqual (List(), IfThenElse(CBool(true), CInt(1), CInt(0)))
  }

  "A function declaration parser" should "parse function declarations correctly" in {
    val incDecl = FDeclaration("increment", "x", Add(Id("x"), CInt(1)))
    parseInput("def increment(x) = x + 1 END") shouldEqual (List(incDecl), Id("END"))
  }

  "A function application parser" should "parse function applications correctly" in {
    parseInput("inc(42)") shouldEqual (List(), App("inc", CInt(42)))
  }

}
