package day11

import day11.Strategy.Nearest8
import org.junit.Test
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class SeatingAreaTest {

    fun readInput(fileName: String): String
            = this::class.java.getResource(fileName).readText(StandardCharsets.UTF_8)

    private fun toSeatingArea(fileName: String, nearestSeatThreshold: Int): SeatingArea {
        val readInput = readInput(fileName)

        val map: Array<Array<String>> =
            readInput.lines().map { it.trim().toCharArray().map { it.toString() }.toTypedArray() }.toTypedArray()
        val m0 = SeatingArea(map, nearestSeatThreshold, Nearest8)
        return m0
    }

    @Test
    fun testMap() {
        val seatingArea = toSeatingArea("/day11test.txt", 3)
        val result = Simulation().solve(seatingArea)
        println(result)
    }

    @Test
    fun solvePart1() {
        val seatingArea = toSeatingArea("/day11.txt", 3)
        val result = Simulation().solve(seatingArea)
        println(result)
    }

    @Test
    fun testEmpty() {
        val seatingArea = toSeatingArea("/day11test3.txt", 4)
        assertTrue(seatingArea.surroundingCoords(Coord(3, 3)).isEmpty())
    }

    @Test
    fun testOnlySeeOne() {
        val seatingArea = toSeatingArea("/day11test4.txt", 4)
        val surroundingCoords = seatingArea.surroundingCoords(Coord(1,1))
        println(surroundingCoords)
        assertEquals(surroundingCoords.size, 1)
        assertEquals(surroundingCoords[0], Coord(3,1))

    }
    
    

    @Test
    fun part2() {
        println(Simulation().solve(toSeatingArea("/day11.txt", 4)))
    }

}

fun List<MutableList<String>>.display() {
    this.forEach {
        it.forEach {
            print(it)
        }
        println()
    }
}