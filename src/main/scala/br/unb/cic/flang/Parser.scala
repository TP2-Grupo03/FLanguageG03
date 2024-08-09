package br.unb.cic.flang

import scala.util.parsing.combinator._
// import scala.util.matching.Regex

import cats.data._
import cats.implicits._

import MErr._
import MErr.eh._

import Declarations._

object FLangParser extends RegexParsers {


    // Parse numbers
    def number: Parser[CInt] = """(0|[1-9]\d*)""".r ^^ {
        num => CInt(num.toInt)
    }

    // Parse booleans
    def bool: Parser[CBool] = ("true" | "false") ^^ {
        case "true" => CBool(true)
        case "false" => CBool(false)
    }

    // Ignora espaços em branco
    override def skipWhitespace: Boolean = true
    override val whiteSpace = "[ \t\r\f]+".r

    // Parse string
    def alpha: Parser[String] = """[a-zA-Z]""".r

    // Parse identifiers
    def identifier: Parser[Id] = """^[a-zA-Z_]\w*""".r ^^ { str => Id(str) }

    // Parse expressions within parentheses
    def parens: Parser[Expr] = "(" ~> expr <~ ")"

    // Unidades básicas do parser
    // https://medium.com/@t.m.h.v.eijk/programming-in-scala-chapter-31-combinator-parsing-60732d17c162
    def factor: Parser[Expr] = number | bool | identifier | parens

    // Parser para termos de uma adição
    def term: Parser[Expr] = factor ~ rep("*" ~ factor) ^^ {
        case base ~ list =>
            val pairs = list.collect { case "*" ~ right => right }
            pairs.foldLeft(base)(Mul)
    }

    def expr: Parser[Expr] =  functionApp | ifThenElse | term ~ rep("+" ~ term) ^^ {
        case base ~ list =>
            val pairs = list.collect { case "+" ~ right => right }
            pairs.foldLeft(base)(Add)
    }

    // Erros de compilação

    // Not implemented as ![EXPRESSION]
    // def notExpr: Parser[Expr] = "!" ~> bool ^^ { 
    //     case CBool(v) => Not(CBool(v))
    //     case _=> throw new RuntimeException("Unexpected match in Not parser")
    // }

    // def andExpr: Parser[Expr] = bool ~ rep("&&" ~ bool) ^^ {
    //     case base ~ list =>
    //         val pairs = list.collect { case "&&" ~ right => right }
    //         pairs.foldLeft(base)(And) 
    // }

    // def orExpr: Parser[Expr] = andExpr ~ rep("||" ~ andExpr) ^^ {
    //     case base ~ list => list.foldLeft(base)(Or)
    // }

    def ifThenElse: Parser[Expr] = "if" ~> expr ~ "then" ~ expr ~ "else" ~ expr ^^ {
        case cond ~ "then" ~ tExpr ~ "else" ~ fExpr => IfThenElse(cond, tExpr, fExpr)
        case _ => throw new RuntimeException("Unexpected match in ifThenElse parser")
    }

    def functionApp: Parser[Expr] = alpha ~ "(" ~ expr ~ ")" ^^ {
        case funName ~ "(" ~ arg ~ ")" => App(funName, arg)
        case _ => throw new RuntimeException("Unexpected match in functionApp parser")
    }

    def declaration: Parser[FDeclaration] = "def" ~> alpha ~ ("(" ~> alpha <~ ")") ~ ("=" ~> expr) ^^ {
        case name ~ arg ~ body => FDeclaration(name, arg, body)
        case _ => throw new RuntimeException("Unexpected match in declaration parser")
    }

    // Parse um programa completo com declarações e expressões
    def program: Parser[(List[FDeclaration], Expr)] = rep(declaration) ~ expr ^^ {
        case decls ~ e => (decls, e)
    }

    // Parse uma string de entrada
    def parseInput(input: String): ParseResult[(List[FDeclaration], Expr)] = parseAll(program, input)
}
