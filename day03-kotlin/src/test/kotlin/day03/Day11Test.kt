package day03

import day11.day11.Day11
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals

internal class Day11Test {

    fun readInput(fileName: String): String
            = this::class.java.getResource(fileName).readText(StandardCharsets.UTF_8)

    @org.junit.Test
    fun testMap() {
        val readInput = readInput("/day11test.txt")
        
        var map : List<MutableList<String>> = readInput.lines().map { it.trim().toCharArray().map { it.toString() }.toMutableList() }
        map.display()
        println()
        println()
        val day11 = Day11(map)
        val m1 = day11.occupyAll()
        val m2 = Day11(m1).evictAll()
        m2.display()
        
        assertEquals(Day11(m1), Day11(m1))

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