package day18

import Util.Companion.readInput
import org.junit.Assert.*
import org.junit.Test

class Day18Test {

    val input = readInput("/day18/day18.txt")


    private val operatorPrecedence1 = mapOf(
        Pair('*', 2),
        Pair('+', 2),
        Pair('(', 1),
        Pair(')', 1)
    )

    private val operatorPrecedence2 = mapOf(
        Pair('*', 2),
        Pair('+', 3),
        Pair('(', 1),
        Pair(')', 1)
    )

    @Test
    fun part1() {
        val parser = Parser(operatorPrecedence1)
        assertEquals(12240, evalPostfixx(parser.exec("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")))
        assertEquals(13632, evalPostfixx(parser.exec("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")))

        val result = input.lines().map { evalPostfixx(parser.exec(it)) }.sum()
        println(result)
    }

    @Test
    fun part2() {
        val parser = Parser(operatorPrecedence2 )
        assertEquals(669060, evalPostfixx(parser.exec("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")))

        val result = input.lines().map { evalPostfixx(parser.exec(it)) }.sum()
        println(result)
    }
}