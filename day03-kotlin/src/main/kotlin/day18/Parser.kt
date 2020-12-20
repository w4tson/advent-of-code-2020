package day18

import java.util.*

fun evalPostfixx(expr : String) : Long {
    val stack : Stack<Long> = Stack()

    expr.toCharArray().forEach { c -> 
        when (c) {
            in '0'..'9' -> stack.push(Character.getNumericValue(c).toLong())
            '*' -> {
                stack.push(stack.pop() * stack.pop())
            }
            '+' -> {
                stack.push(stack.pop() + stack.pop())
            }
        }
    }
    
    return stack.pop()
}

class Parser(val operatorPrecedence : Map<Char, Int>) {
    
    fun exec(expression: String) : String {
        var postfixList = ""
        val opStack = Stack<Char>()

        expression.filter { it != ' ' }.forEach { c ->
            if (Character.getNumericValue(c) in 0..9) {
                postfixList += c
            } else if (c == '(') {
                opStack.push(c)
            } else if (c == ')') {
                var topToken = if (opStack.empty()) '(' else opStack.pop()
                while (topToken != '(' && opStack.isNotEmpty() && operatorPrecedence[opStack.peek()]!! >= operatorPrecedence[c]!! ) {
                    postfixList += topToken
                    topToken = if (opStack.empty()) '(' else opStack.pop() 
                }
            } else {
                while (opStack.isNotEmpty() && opStack.isNotEmpty() && operatorPrecedence[opStack.peek()]!! >= operatorPrecedence[c]!! ) {
                    postfixList += opStack.pop()
                }
                opStack.push(c)
            }
        }

        while (!opStack.isEmpty()) {
            postfixList += opStack.pop()
        }
        
        return postfixList
    }
}