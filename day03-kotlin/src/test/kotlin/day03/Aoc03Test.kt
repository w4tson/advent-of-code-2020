package day03

import org.junit.Test
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals


internal class Aoc03Test {
    fun readInput(fileName: String): String
            = this::class.java.getResource(fileName).readText(StandardCharsets.UTF_8)


    @org.junit.Test
    fun testMap() {
        val readInput = readInput("/testinput.txt")
        val map = GeologyMap(readInput)
        assertEquals('.', map.getGeologyAt(0,0))
        assertEquals('.', map.getGeologyAt(0,10))
        assertEquals('#', map.getGeologyAt(13,0))
        assertEquals('#', map.getGeologyAt(10,10))
        assertEquals('.', map.getGeologyAt(11,10))
        assertEquals('#', map.getGeologyAt(12,10))
    }

    @Test
    fun testInputShouldHave7Trees() {
        val result = Part01("/testinput.txt").solve( Pair(3,1))
        assertEquals(7, result)
    }

    @Test
    fun part1() {
        val result = Part01("/input.txt").solve( Pair(3,1))
        println(result)
    }

    @Test
    fun part2() {
        val part01 = Part01("/input.txt")
        val result = arrayOf(part01.solve( Pair(1,1)), 
        part01.solve(Pair(3,1)), 
        part01.solve(Pair(5,1)), 
        part01.solve(Pair(7,1)), 
        part01.solve(Pair(1,2)))
        println(result.fold(1, { acc, item -> acc*item}))
    }
}