package day12

import Util.Companion.readInput
import day12.Inst.*
import org.junit.Test
import kotlin.test.assertEquals


internal class Day12Test {
    
    @Test
    fun test() {
        assertEquals(25, steerPart01(toMoves("/day12/day12test.txt")))
    }

    @Test
    fun testLocationAvances() {
        val result = Location(0, 0, North).advance(Move(East,   10))
        assertEquals(Location(10, 0, North), result)

        val result2 = Location(0, 0, South).advance(Move(Forward,   10))
        assertEquals(Location(0, 10, South), result2)

        assertEquals(South, Location(0, 0, East).turnClockwise(90).facing)
        assertEquals(South, Location(0, 0, North).turnAntiClockwise(180).facing)
    }

    @Test
    fun part01() {
        println(steerPart01(toMoves("/day12/day12.txt")))
    }
    
    @Test
    fun part02() {
        println(steerPart02(toMoves("/day12/day12.txt")))
    }

    private fun toMoves(fileName : String): List<Move> {
        return readInput(fileName).lines()
            .map { Move(Inst.fromStr(it.substring(0, 1))!!, it.substring(1).toInt()) }
    }
}