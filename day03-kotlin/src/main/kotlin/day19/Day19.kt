package day19

import java.lang.IllegalStateException


data class RuleMatch(val index1: Int, val index2: Int)

sealed class Rule(val index: Int)

data class Value(val id: Int, val value : String) : Rule(id) 
data class EitherMatch(val id: Int, val r1 : RuleMatch, val r2: RuleMatch) : Rule(id)
data class SingleEitherMatch(val id: Int, val v1 : Int, val v2: Int) : Rule(id)

data class TripeMatch(val id: Int, val r1 : RuleMatch, val index3: Int) : Rule(id)
data class DoubleMatch(val id: Int, val r1 : RuleMatch) : Rule(id)
data class SingleMatch(val id: Int, val v1: Int)  : Rule(id)

typealias Rules = Map<Int, Rule>

fun matches(s : String, rules: Rules) : Boolean {
    val m = _matches(s, 0, rules, 0)
    return m.second && s.length == m.first 
} 

fun _matches(s : String, ruleId: Int, rules : Map<Int, Rule>, index: Int) : Pair<Int, Boolean> {
    val rule = rules.get(ruleId)
    val res : Pair<Int, Boolean> = when (rule) {
        is Value -> Pair(index+1, s[index] == rule.value[0])
        is SingleMatch -> _matches(s, rule.v1, rules, index)
        is DoubleMatch -> {
                listOf(rule.r1.index1, rule.r1.index2).fold(Pair(index, true), { acc, rId ->
                    val (newIndex, bool) = _matches(s, rId, rules, acc.first)
                    Pair(newIndex, acc.second && bool)
                })
        }
        is TripeMatch -> {
            listOf(rule.r1.index1, rule.r1.index2, rule.index3).fold(Pair(index, true), { acc, rId ->
                val (newIndex, bool) = _matches(s, rId, rules, acc.first)
                Pair(newIndex, acc.second && bool)
            })
        }
        is SingleEitherMatch -> {
            val m1 = _matches(s, rule.v1, rules, index)
            if (m1.second) m1 else {
                _matches(s, rule.v2, rules, index)    
            }
        }
        is EitherMatch -> {
            val m1 = listOf(rule.r1.index1, rule.r1.index2).fold(Pair(index, true), { acc, rId ->
                val (newIndex, bool) = _matches(s, rId, rules, acc.first)
                Pair(newIndex, acc.second && bool)
            })
            if (m1.second) m1 else {
                listOf(rule.r2.index1, rule.r2.index2).fold(Pair(index, true), { acc, rId ->
                    val (newIndex, bool) = _matches(s, rId, rules, acc.first)
                    Pair(newIndex, acc.second && bool)
                }) 
            }
        }
        else -> throw IllegalStateException("Nope")
    }
    return res
    
}

