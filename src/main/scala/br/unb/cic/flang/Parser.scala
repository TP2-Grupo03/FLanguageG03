package br.unb.cic.flang

import scala.util.parsing.combinator._
// import scala.util.matching.Regex

import cats.data._
import cats.implicits._

import MErr._
import MErr.eh._

import Declarations._

object FLangParser extends RegexParsers {


    def number: Parser[CInt] = """(0|[1-9]\d*)""".r ^^ {
        num => CInt(num.toInt)
    }

    def bool: Parser[CBool] = ("true" | "false") ^^ {
        case "true" => CBool(true)
        case "false" => CBool(false)
    }

    // Handle whitespace
    override def skipWhitespace: Boolean = true
    override val whiteSpace = "[ \t\r\f]+".r

    // Just for combination with other parsers
    def string: Parser[String] = """[a-zA-Z_]\w*""".r ^^ {
        str => str.toString
    }

    def parens: Parser[Expr] = "(" ~> expr <~ ")"

    def identifier: Parser[Expr] = string ^^ { case name => Id(name) }
    
    def ifThenElse: Parser[Expr] = "if" ~> expr ~ "then" ~ expr ~ "else" ~ expr ^^ {
        case cond ~ "then" ~ tExpr ~ "else" ~ fExpr => IfThenElse(cond, tExpr, fExpr)
        case _ => throw new RuntimeException("Unexpected match in ifThenElse parser")
    }

    // Parse function applications
    def functionApp: Parser[Expr] = string ~ "(" ~ expr ~ ")" ^^ {
        case funName ~ "(" ~ arg ~ ")" => App(funName, arg)
        case _ => throw new RuntimeException("Unexpected match in functionApp parser")
    }

    // https://medium.com/@t.m.h.v.eijk/programming-in-scala-chapter-31-combinator-parsing-60732d17c162
    def factor: Parser[Expr] = functionApp | ifThenElse | number | bool | parens | identifier

    def term: Parser[Expr] = factor ~ rep("*" ~ factor) ^^ {
        case base ~ list =>
            val pairs = list.collect { case "*" ~ right => right }
            pairs.foldLeft(base)(Mul)
    }

    def expr: Parser[Expr] = term ~ rep("+" ~ term) ^^ {
        case base ~ list =>
            val pairs = list.collect { case "+" ~ right => right }
            pairs.foldLeft(base)(Add)
    }

    // Parse function declaration
    def declaration: Parser[FDeclaration] = "def" ~> string ~ "(" ~ string ~ ")" ~ "=" ~ expr ^^ {
        case name ~ "(" ~ arg ~ ")" ~ "=" ~ body => FDeclaration(name, arg, body)
    }

    // Parse all program
    def program: Parser[(List[FDeclaration], Expr)] = rep(declaration) ~ expr ^^ {
        case decls ~ exp => (decls, exp)
    }

    // Parse simple string input
    def parseInput(input: String): (List[FDeclaration], Expr) = parseAll(program, input) match {
        case Success(result, _) => result
        case failure: NoSuccess => scala.sys.error(failure.msg)
    }
}
