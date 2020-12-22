package day19

import Util.Companion.readInput
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Day19Test {
    
    val r = Regex("^(\\d+): (\\d+) (\\d+)(?: \\| (\\d+) (\\d+))?$")
    val singleEther = Regex("^(\\d+): (\\d+) \\| (\\d+)$")
    val triple = Regex("^(\\d+): (\\d+) (\\d+) (\\d+)$")
    val singleVal = Regex("(\\d+): \"(\\w+)\"")
    val singleIndex = Regex("^(\\d+): (\\d+)$")

    private fun buildRules(ruleStr: String): Rules = ruleStr.lines().map { toRule(it) }.associateBy { it.index }

    fun toRule(s: String) : Rule {
//        println(s)
        if (s.contains("\"")) {
            val (id, value) = singleVal.find(s)!!.destructured
            return Value(id.toInt(), value)
        } else if (singleIndex.matches(s)) {
            val (index, rule1) = singleIndex.find(s)!!.destructured
            return SingleMatch(index.toInt(), rule1.toInt())
        } else if (singleEther.matches(s)) {
            val (index, rule1, rule2) = singleEther.find(s)!!.destructured
            return SingleEitherMatch(index.toInt(), rule1.toInt(), rule2.toInt())
        } else if (triple.matches(s)) {
            val (index, rule1, rule2, rule3) = triple.find(s)!!.destructured
            return TripeMatch(index.toInt(), RuleMatch(rule1.toInt(), rule2.toInt()), rule3.toInt())
        } else  {
            val match = r.find(s)!!
            val (index, rule1, rule2, rule3, rule4) = match.destructured
            val r1 = RuleMatch(rule1.toInt(), rule2.toInt())
            if (rule3 == "") {
                return DoubleMatch(index.toInt(), r1)
            } else {
                return EitherMatch(index.toInt(), r1, RuleMatch(rule3.toInt(), rule4.toInt()))
            }
        }
    }

    @Test
    fun part1example() {
        val (ruleStr, input) = readInput("/day19/test.txt").split("\n\n")
        val rules = buildRules(ruleStr)
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
//        val rules = buildRules(ruleStr.replace("8: 42", "8: 42 | 42 8").replace("11: 42 31", ))
        
//        println(count)
//        8: 42
    }
}