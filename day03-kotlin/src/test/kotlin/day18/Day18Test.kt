package day18

import Util.Companion.readInput
import org.junit.Assert.*
import org.junit.Test

class Day18Test {
    
    @Test
    fun testBinaryTree() {
        val str = readInput("/day18/day18test.txt")
        println(str)
//        Parser(str).parse()
        
        //1 + (2 * 3) + (4 * (5 + 6))
        val left = Sum(Const(1), Mul(Const(2), Const(3)))
        val right = Mul(Const(4), Sum(Const(5), Const(6)))
        assertEquals(51, eval(Sum(left, right)))
    }

    @Test
    fun name() {
        val str = readInput("/day18/day18test.txt")
//        println(Parser("1+1")())
        assertEquals(10, eval(Parser("5*(1+1)")()))
        assertEquals(26, eval(Parser("2*3+(4*5)")()))
        assertEquals(13632, eval(Parser("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")()))
        val result = str.lines().map { eval(Parser(it)()) }.sum()
        println(result)

    }
}