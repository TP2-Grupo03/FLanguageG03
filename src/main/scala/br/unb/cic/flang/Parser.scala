package br.unb.cic.flang

import scala.util.parsing.combinator._
// import scala.util.matching.Regex

import cats.data._
import cats.implicits._

import MErr._
import MErr.eh._

import Declarations._

object FLangParser extends RegexParsers {


    def number: Parser[CInt] = """\d+""".r ^^ {
        num => CInt(num.toInt)
    }

    def bool: Parser[CBool] = ("true" | "false") ^^ {
        case "true" => CBool(true)
        case "false" => CBool(false)
    }

    override def skipWhitespace: Boolean = true
    // override val whiteSpace = "[\t\r\f]+".r
    override val whiteSpace = """\s+""".r

    def string: Parser[String] = """[a-z]+""".r ^^ { _.toString }

    // Parse identifiers
    def identifier: Parser[Id] = """^[a-zA-Z_]\w*""".r ^^ { str => Id(str) }

    // Parse expressions within parentheses
    def parens: Parser[Expr] = "(" ~> expr <~ ")"

    // Basic units of expressions
    def factor: Parser[Expr] = number | bool | identifier | parens

    def term: Parser[Expr] = factor ~ rep("*" ~ factor) ^^ {
        case base ~ list =>
            val pairs = list.collect { case "*" ~ right => right }
            pairs.foldLeft(base)(Mul)
    }

    def expr: Parser[Expr] = ifThenElse | term ~ rep("+" ~ term) ^^ {
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

    def functionApp: Parser[Expr] = identifier ~ "(" ~ expr ~ ")" ^^ {
        case Id(name) ~ "(" ~ arg ~ ")" => App(name, arg)
        case _ => throw new RuntimeException("Unexpected match in functionApp parser")
    }

    def declaration: Parser[FDeclaration] = "def" ~ string ~ "(" ~ string ~ ")" ~ "=" ~ expr ^^ {
        case "def" ~ name ~ "(" ~ arg ~ ")" ~ "=" ~ body => FDeclaration(name, arg, body)
        case _ => throw new RuntimeException("Unexpected match in declaration parser")
    }

    // Parse a program with declarations and an expression
    def program: Parser[(List[FDeclaration], Expr)] = rep(declaration) ~ expr ^^ {
        case decls ~ e => (decls, e)
    }

    // Parse a given input string
    def parseInput(input: String): ParseResult[(List[FDeclaration], Expr)] = parseAll(program, input)
}
