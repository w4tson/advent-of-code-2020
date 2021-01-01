package day23

import org.junit.Test
import kotlin.test.assertEquals

class Day23Test{
    
    @Test
    fun example() {
        println(part1(CircularList.from("389125467"), 10))
    }

    @Test
    fun part1() {
        println(part1(CircularList.from("792845136"), 100))
    }

    @Test
    fun part2Example() {
        assertEquals(149245887792, part2(CircularList.from(listOf(3, 8, 9, 1, 2, 5, 4, 6, 7) + (10..1_000_000))))
    }

    @Test
    fun part2() {
        println(part2(CircularList.from(listOf(7, 9, 2, 8, 4, 5, 1, 3, 6) + (10..1_000_000))))
    }

    @Test
    fun circularListTest() {
        val circularList = CircularList.from(listOf(1, 3, 5, 7,9,11,4))

        val curr = circularList.find { it.data == 5 }
        val curr2 = circularList.find { it.data == 11 }!!
        println(curr)
        println(circularList.contains(2))
        println(circularList.contains(1))
        println(circularList)
        val spliced = circularList.cutNFrom(3, curr2)
        println(spliced)
        println(circularList)
        circularList.insertAt(circularList.first(), spliced)
        println(circularList)
                
        circularList.take(8).forEach { println(it) }

        listOf(1,2,3).contains(1)
    }

}