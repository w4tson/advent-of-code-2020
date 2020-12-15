package day15

import naturalNumbers
import org.junit.Test
import kotlin.test.assertEquals

class Day15Test {

    @Test
    fun part1Exampe() {
        assertEquals(436, game2(listOf(0, 3, 6), 2020))
//        assertEquals(1, game2(listOf(1,3,2),2020))
//        assertEquals(10, game2(listOf(2,1,3), 2020))
        val result = game2(listOf(1,0,16,5,17,4), 30000000)
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