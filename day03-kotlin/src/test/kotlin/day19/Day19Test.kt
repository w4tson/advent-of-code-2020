package day19

import Util.Companion.readInput
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day19Test {
    
    val singleVal = Regex("(\\d+): \"(\\w+)\"")

    private fun buildRules(ruleStr: String): Rules = ruleStr.lines().map { toRule(it) }.associateBy { it.index }

    fun toRule(s: String) : Rule {
//        println(s)
        if (s.contains("|")) {
            val (ruleId, data) = s.split(":")
            val (a,b) = data.split("|")
            return Or(ruleId.toInt(), extractIds(a), extractIds(b))
        }
        else if (s.contains("\"")) {
            val (id, value) = singleVal.find(s)!!.destructured
            return Value(id.toInt(), value)
        } else {
            val (ruleId, data) = s.split(":")
            return And(ruleId.toInt(), extractIds(data))
        } 
    }

    private fun extractIds(a: String) : List<Int> = a.trim().split(" ").map { it.toInt() }.toList()

    @Test
    fun part1example() {
        val (ruleStr, input) = readInput("/day19/test.txt").split("\n\n")
        val rules = buildRules(ruleStr)
        println(rules)
        val lines = input.lines()
        assertTrue(matches(lines[0],  rules))
        assertFalse(matches(lines[1], rules))
        assertTrue(matches(lines[2],  rules))
        assertFalse(matches(lines[3], rules))
        assertFalse(matches(lines[4], rules))
    }

    @Test
    fun part1() {
        val (ruleStr, input) = readInput("/day19/day19.txt").split("\n\n")
        val rules = buildRules(ruleStr)
        val count = input.lines().count { matches(it,rules) }
        println(count)
    }

    @Test
    fun part2() {
        val (ruleStr, input) = readInput("/day19/day19.txt").split("\n\n")
        val ruleStr1 = ruleStr.replace("8: 42", "8: 42 | 42 8").replace("11: 42 31", "11: 42 31 | 42 11 31")
        val rules = buildRules(ruleStr1)
        val count = input.lines().count { matches(it, rules) }
        println(count)
    }
}