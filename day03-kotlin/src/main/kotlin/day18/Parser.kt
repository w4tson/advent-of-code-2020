package day18

import java.util.*

sealed class Expr
data class Const(var value: Int) : Expr()
data class Sum(var left: Expr, var right: Expr) : Expr()
data class Mul(var left: Expr, var right: Expr) : Expr()

fun eval(expr: Expr): Int = when(expr) {
    is Const -> expr.value
    is Sum -> eval(expr.left) + eval(expr.right)
    is Mul -> eval(expr.left) * eval(expr.right)
}

fun Char.isOperand() : Boolean = this == '+' || this == '*'

class Parser(val expression: String) {
    var s : Stack<Char> = Stack()
    var e : Stack<Expr> = Stack()
    
    
    // 1 + (2 * 3) + (4 * (5 + 6))
    // (1 + 1 ) * 5
    operator fun invoke() : Int {
        expression.forEach { 
            // consts to e stack
            //
            if (it.isDigit()) {
                e.push(Const(Character.getNumericValue(it)))
            } else {
                s.push(it)
            }
            
            if (s.isNotEmpty() && s.peek().isOperand() && e.size >= 2){
                val right = e.pop()
                val left = e.pop()
                e.push(when (s.pop()) {
                    '+' -> Sum(left, right)
                    else -> Mul(left, right)
                })
            }
            println("s = $s")
            println("e = $e")
        }

        val expr = e.pop()
        println(expr)
        return eval(expr)
    }

    
    
    
    
    //1 + (2 * 3) + (4 * (5 + 6))
    
    //
    
}