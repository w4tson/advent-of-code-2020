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
        println(Parser("1+1")())
        println(Parser("(1+1)*5")())
    }
}