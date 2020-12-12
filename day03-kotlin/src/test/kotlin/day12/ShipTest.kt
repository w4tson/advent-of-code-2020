package day12

import day11.Coord
import day12.Inst.*
import org.junit.Test
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals


internal class ShipTest {
   
    fun readInput(fileName: String): String
            = this::class.java.getResource(fileName).readText(StandardCharsets.UTF_8)
    
    @Test
    fun test() {
        val moves = toMoves("/day12.txt")

        val dist = steer(moves)
        println(dist)

    }

    @Test
    fun standard() {
        val result = Location(0, 0, North).advance(Move(East,   10))
        assertEquals(Location(10, 0, North), result)

        val result2 = Location(0, 0, South).advance(Move(Forward,   10))
        assertEquals(Location(0, 10, South), result2)

        repeatRange(4).take(10).forEach { println(it) }
//        Direction.values().cycle().take(10).forEach { println(it) }
        val newLoc = Location(0, 0, Inst.East).rotateClocwise(90)
        println(newLoc)
        println(Location(0, 0, Inst.North).rotateAntiClocwise(180))
    }
    
    

    private fun toMoves(fileName : String): List<Move> {
        val inputStr = readInput(fileName)
        return inputStr.lines()
            .map { Move(Inst.fromStr(it.substring(0, 1))!!, it.substring(1).toInt()) }
    }
}