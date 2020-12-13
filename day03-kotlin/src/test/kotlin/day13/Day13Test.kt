package day13

import Util.Companion.readInput
import org.junit.Test
import kotlin.test.assertEquals

class Day13Test {
    
    
    
    fun parseBusTimetable(fileName: String) : Pair<Int, List<Int>> {
        val lines = readInput(fileName).lines()
        println(lines)
        return Pair(lines[0].toInt(), lines[1].split(",").filter { it != "x" }.map { it.toInt() })
    }
    
    
    @Test
    fun testExamples() {
        val (arrivalTime, timetable) = parseBusTimetable("/day13/day13test.txt")
        assertEquals(295, part01(arrivalTime, timetable))
    }

    @Test
    fun part01() {
        val (arrivalTime, timetable) = parseBusTimetable("/day13/day13.txt")
        println(part01(arrivalTime, timetable))
    }

    @Test
    fun part02() {
        val timetable = readInput("/day13/day13.txt").lines()[1].split(",")
            .map { when (it) {
                "x" -> 0L
                else -> it.toLong()    
            }}

        val result = part02(timetable)
        println(result)
    }
}