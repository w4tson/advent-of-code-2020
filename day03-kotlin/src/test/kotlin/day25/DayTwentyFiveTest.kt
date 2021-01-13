package day25

import org.junit.Assert.*
import org.junit.Test

class DayTwentyFiveTest{

    val cardPublicKey = 5764801L
    val doorPublicKey = 17807724L

    @Test
    fun example() {
        assertEquals(8L, calcLoops(cardPublicKey))
        assertEquals(11L, calcLoops(doorPublicKey))
        assertEquals(14897079L, part1(cardPublicKey, doorPublicKey))
    }

    @Test
    fun runPart1() {
        println(part1(10604480, 4126658))
    }
}