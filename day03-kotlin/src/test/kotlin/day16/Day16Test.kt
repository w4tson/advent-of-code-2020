package day16

import Util.Companion.readInput
import org.junit.Test

class Day16Test {

    @Test
    fun part01() {
        val input = readInput("/day16/day16.txt")
        val parts = input.split("\n\n")
        val rules = parts[0].lines().map { toRule(it) }
        val tickets = parts[2].lines()
            .drop(1)
            .map { it.split(",").map(String::toInt).toList() }
            .map{ Ticket(it) }

        val myTicket = parts[1].lines()
            .drop(1)
            .map { it.split(",").map(String::toInt).toList() }
            .map{ Ticket(it) }
            .first()

        val day16 = Day16(rules)

//        val errorRates = day16.errorRates(tickets)
//        println(errorRates)

        day16.part2(tickets + myTicket)
    }
}