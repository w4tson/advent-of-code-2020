package day19

import Util.Companion.readInput
import org.junit.Test

class Day19Test {
    
    val r = Regex("^(\\d+): (\\d+) (\\d+)(?: \\| (\\d+) (\\d+))?$")
    val singleEther = Regex("^(\\d+): (\\d+) \\| (\\d+)$")
    val singleVal = Regex("(\\d+): \"(\\w+)\"")
    val singleIndex = Regex("^(\\d+): (\\d+)$")


    fun toRule(s: String) : Rule {
        if (s.contains("\"")) {
            val (id, value) = singleVal.find(s)!!.destructured
            return Value(id.toInt(), value)
        } else if (singleIndex.matches(s)) {
            val (index, rule1) = singleIndex.find(s)!!.destructured
            return SingleMatch(index.toInt(), rule1.toInt())
        } else if (singleEther.matches(s)) {
            val (index, rule1, rule2) = singleEther.find(s)!!.destructured
            return SingleEitherMatch(index.toInt(), rule1.toInt(), rule2.toInt())
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
    fun part1() {
        val (ruleStr, input) = readInput("/day19/day19.txt").split("\n\n")
        val rules = ruleStr.lines().map { toRule(it) }.associateBy { it.index }
        val reg = Regex(buildRegex(rules))
        val count = input.lines().count { reg.matches(it) }
        println(count)
    }
}