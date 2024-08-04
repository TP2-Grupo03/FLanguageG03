package br.unb.cic.flang

import org.scalatest._
import flatspec._
import matchers._

import FLangParser._


class ParserTests extends AnyFlatSpec with should.Matchers {

  val inc = FDeclaration("inc", "x", Add(Id("x"), CInt(1)))
  val bug = FDeclaration("bug", "x", Add(Id("y"), CInt(1)))

  val declarations = List(inc, bug)

  "An integer parser" should "parse integers correctly" in {
    parseInput("42") match {
      case Success(res, next) =>
        res shouldEqual (List(), CInt(42))
        next.atEnd shouldBe true 
      case _=> fail()
    }
  }

  "A boolean parser" should "parse booleans correctly" in {
    parseInput("true") match {
      case Success(res, next) =>
        res shouldEqual (List(), CBool(true))
        next.atEnd shouldBe true 
      case _=> fail()
    }
    parseInput("false") match {
      case Success(res, next) =>
        res shouldEqual (List(), CBool(false))
        next.atEnd shouldBe true 
      case _=> fail()
    }
  }

  "An addition parser" should "parse addition expressions correctly" in {
    parseInput("5 + 10") match {
      case Success(res, next) =>
        res shouldEqual (List(), Add(CInt(5), CInt(10)))
        next.atEnd shouldBe true 
      case _=> fail()
    }
  }

  "A multiplication parser" should "parse multiplication expressions correctly" in {
    parseInput("5 * 10") match {
      case Success(res, next) =>
        res shouldEqual (List(), Mul(CInt(5), CInt(10)))
        next.atEnd shouldBe true 
      case _=> fail()
    }
  }

  // "A logical AND parser" should "parse AND expressions correctly" in {
  //   parseInput("true && false") match {
  //     case Success(res, next) =>
  //       res shouldEqual (List(), And(CBool(true), CBool(false)))
  //       next.atEnd shouldBe true 
  //     case _=> fail()
  //   }
  // }

  // "A logical OR parser" should "parse OR expressions correctly" in {
  //   parseInput("true || false") match {
  //     case Success(res, next) =>
  //       res shouldEqual (List(), Or(CBool(true), CBool(false)))
  //       next.atEnd shouldBe true 
  //     case _=> fail()
  //   }
  // }

  // "A logical NOT parser" should "parse NOT expressions correctly" in {
  //   parseInput("!true") match {
  //     case Success(res, next) =>
  //       res shouldEqual (List(), Not(CBool(true)))
  //       next.atEnd shouldBe true 
  //     case _=> fail()
  //   }
  // }

  "An if-then-else parser" should "parse if-then-else expressions correctly" in {
    parseInput("if true then 1 else 0") match {
      case Success(res, next) =>
        res shouldEqual (List(), IfThenElse(CBool(true), CInt(1), CInt(0)))
        next.atEnd shouldBe true 
      case _=> fail()
    }
  }

  // "A function declaration parser" should "parse function declarations correctly" in {
  //   val incDecl = FDeclaration("increment", "x", Add(Id("x"), CInt(1)))
  //   println(parseInput("def increment(x) = x + 1"))
  //   parseInput("def increment(x) = x + 1") match {
  //     case Success(res, next) =>
  //       res shouldEqual (List(incDecl), incDecl)
  //       next.atEnd shouldBe true 
  //     case _=> fail()
  //   }
  // }
  
  // "A function application parser" should "parse function applications correctly" in {
  //   parseInput("inc(42)") match {
  //     case Success(res, next) =>
  //       res shouldEqual (declarations, App("inc", CInt(42)))
  //       next.atEnd shouldBe true 
  //     case _=> fail()
  //   }
  // }
}
