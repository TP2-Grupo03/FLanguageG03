package br.unb.cic.flang

object Main extends App {
    val expr = IfThenElse(
    cond = CInt(1),
    thenExpr = Add(CInt(3), CInt(4)), // 3 + 4 = 7
    elseExpr = Mul(CInt(2), CInt(2)) // 2 * 2 = 4
    )

    val result = Interpreter.runEval(expr, List())
    println(result)
}