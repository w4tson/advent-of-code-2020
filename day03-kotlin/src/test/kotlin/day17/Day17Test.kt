package day17

import Util.Companion.readInput
import org.junit.Test

class Day17Test {
    
    @Test
    fun part1() {
        val pd = toPocketDimension(readInput("/day17/day17.txt"))
        val count = generateSequence(pd) { it.next() }.take(7).toList().last().map.values.count { it }
        println(count)
    }

    @Test
    fun part2() {
        val pd = toHyperCube(readInput("/day17/day17.txt"))
        val count = generateSequence(pd) { it.next() }.take(7).toList().last().map.values.count { it }
        println(count)
    }
}