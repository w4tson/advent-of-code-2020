package day18

import java.util.*

sealed class Expr
data class Const(var value: Long) : Expr()
data class Sum(var left: Expr, var right: Expr) : Expr()
data class Mul(var left: Expr, var right: Expr) : Expr()

fun eval(expr: Expr): Long = when(expr) {
    is Const -> expr.value
    is Sum -> eval(expr.left) + eval(expr.right)
    is Mul -> eval(expr.left) * eval(expr.right)
}

fun Char.isOperand() : Boolean = this == '+' || this == '*'

class Parser(val expression: String) {
    var s : Stack<Char> = Stack()
    var e : Stack<Expr> = Stack()
    var tmp: String = ""

    operator fun invoke(): Expr {
        expression.filter { it != ' ' }.forEach {
            // consts to e stack
            //
            if (tmp == "" && it != '(') {
                if (it.isDigit()) {
                    e.push(Const(Character.getNumericValue(it).toLong()))
                } else {
                    s.push(it)
                }

            } else {
                if (it == ')') {
                    e.push(Parser(tmp)())
                    tmp = ""
                } else if (it == '(') {
                    tmp = " "
                } else {
                    tmp += it
                }
            }

            if (tmp.isEmpty() && s.isNotEmpty() && s.peek().isOperand() && e.size >= 2) {
                val right = e.pop()
                val left = e.pop()
                e.push(
                    when (s.pop()) {
                        '+' -> Sum(left, right)
                        else -> Mul(left, right)
                    }
                )
            }
        }

        return e.pop()
    }
}