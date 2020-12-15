package day15

import naturalNumbers
import org.junit.Test
import kotlin.test.assertEquals

class Day15Test {

    @Test
    fun part1Exampe() {
        assertEquals(436, game(listOf(0, 3, 6)).take(2020).last().last())
        assertEquals(1, game(listOf(1,3,2)).take(2020).last().last())
        assertEquals(10, game(listOf(2,1,3)).take(2020).last().last())
        val result = game(listOf(1,0,16,5,17,4)).take(2020).last().last()
        println(result)
    }

    @Test
    fun name() {
        naturalNumbers().take(100)
            .forEach { print("$it ".padEnd(3)) }
        println()
        game(listOf(0,1))
            .take(2020).last()
            .forEach { print("$it ".padEnd(3)) }
        println()

    }
}