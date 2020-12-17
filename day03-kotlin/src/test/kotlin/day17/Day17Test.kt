package day17

import Util.Companion.readInput
import org.junit.Test

class Day17Test {
    @Test
    fun example() {

        val input = readInput("/day17/day17.txt")
        val pd = toPocketDimension(input)

        val res = generateSequence(pd) { it.next() }.take(7).toList()
        val last = res.last()
        val count = last.map.values.count { it }
        println(count)


//        println(count)
    }
}