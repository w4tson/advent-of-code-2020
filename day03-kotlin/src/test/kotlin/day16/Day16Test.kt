package day16

import Util.Companion.readInput
import org.junit.Test

class Day16Test {

    @Test
    fun name() {
        val input = readInput("/day16/day16.txt")
        val foo = input.split("\n\n")
        val rules = foo[0].lines().map { toRule(it) }
        val tickets = foo[2].lines()
            .drop(1)
            .map { it.split(",").map(String::toInt).toList() }
            .map{ Ticket(it) }
        println(rules)
//        assert(3 in rules[0].range1)
        println(tickets)
        val errorRates = Day16(rules).errorRates(tickets)
        println(errorRates)
    }
}