package day14

import Util.Companion.readInput
import org.junit.Assert.*
import org.junit.Test

class Day14Test {
    @Test
    fun name() {
        val input = readInput("/day14/day14test.txt")
        println(UInt36(11))
        
        assertEquals(73, UInt36(11).applyMask(Mask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X")))
        
        val program1 = Program(parse(input))
        assertEquals(165L, program1())
    }

    @Test
    fun part01() {
        val input = readInput("/day14/day14.txt")
        val program1 = Program(parse(input))
        println(program1())
    }
}