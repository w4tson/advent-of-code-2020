package day21

import Util.Companion.readInput
import day21.Food.Companion.toFood
import org.junit.Test

class Day21Test {
    val input = readInput("/day21/day21.txt")
    val foods = input.lines().map { toFood(it)}
    
    @Test
    fun testPart1() {
        println(part1(foods))
    }

    @Test
    fun testPart2() {
        println(part2(foods))
    }
}