package day03

import day11.SeatingArea
import day11.Simulation
import org.junit.Test
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals

internal class SeatingAreaTest {

    fun readInput(fileName: String): String
            = this::class.java.getResource(fileName).readText(StandardCharsets.UTF_8)

    @Test
    fun testMap() {
        val readInput = readInput("/day11test.txt")
        
        val map : Array<Array<String>> = readInput.lines().map { it.trim().toCharArray().map { it.toString() }.toTypedArray() }.toTypedArray()
        val m0 = SeatingArea(map)
        println()
        println()
        assertEquals(SeatingArea(arrayOf(arrayOf("#"))), SeatingArea(arrayOf(arrayOf("#"))))

        val result = Simulation().solve(m0)
        println(result)
    }

    @Test
    fun example() {
        val readInput = readInput("/day11.txt")
        val map : Array<Array<String>> = readInput.lines().map { it.trim().toCharArray().map { it.toString() }.toTypedArray() }.toTypedArray()
        val result = Simulation().solve(SeatingArea(map))
        println(result)
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