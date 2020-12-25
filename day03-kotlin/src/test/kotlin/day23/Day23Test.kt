package day23

import cycle
import org.junit.Test
import kotlin.streams.toList

class Day23Test{
    @Test
    fun example() {
        println(part1(toCups("389125467"), 10))
    }

    @Test
    fun part1() {
        println(part1(toCups("792845136"), 100))
    }

    private fun toCups(input: String) = input.chars().map { Character.getNumericValue(it) }.toList()
}