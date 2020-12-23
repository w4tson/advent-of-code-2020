package day19

import java.lang.IllegalStateException

sealed class Rule(val index: Int)

data class Value(val id: Int, val value : String) : Rule(id) 
data class Or(val id: Int, val b1 : List<Int>, val b2: List<Int>) : Rule(id)
data class And(val id: Int, val ruleIds: List<Int>)  : Rule(id)

typealias Rules = Map<Int, Rule>

fun matches(s : String, rules: Rules) : Boolean {
    val m = _matches(s, 0, rules, 0, false)
    return m.second && s.length == m.first 
} 

fun _matches(s : String, ruleId: Int, rules : Map<Int, Rule>, index: Int, mtc: Boolean) : Pair<Int, Boolean> {
    val rule = rules[ruleId]
    if (index  > s.length-1) {
        println("$ruleId $index==${s.length} $s")
        return Pair(s.length, !mtc) 
    }
    val res : Pair<Int, Boolean> = when (rule) {
        is Value -> Pair(index + 1, s[index] == rule.value[0])
        is And -> {
            matchAnd(rule, index, s, rules, mtc)
        }
        is Or -> {
            val m1 = rule.b1.fold(Pair(index, true), { acc, rId ->
                val (newIndex, bool) = _matches(s, rId, rules, acc.first, mtc)
                Pair(newIndex, acc.second && bool)
            })
            if (m1.second) m1 else if (ruleId == 11) {
                rule.b2.fold(Pair(index, true), { acc, rId ->
                    val (newIndex, bool) = _matches(s, rId, rules, acc.first, rId == 11 || rId == 42)
                    Pair(newIndex, acc.second && bool)
                })
            } else {
                rule.b2.fold(Pair(index, true), { acc, rId ->
                    val (newIndex, bool) = _matches(s, rId, rules, acc.first, mtc)
                    Pair(newIndex, acc.second && bool)
                })
            }
        }
        else -> throw IllegalStateException("Nope")
    }
    return res
    
}

private fun matchAnd(
    rule: And,
    index: Int,
    s: String,
    rules: Map<Int, Rule>,
    mtc: Boolean
): Pair<Int, Boolean> = rule.ruleIds.fold(Pair(index, true), { acc, rId ->
    val (newIndex, bool) = _matches(s, rId, rules, acc.first, mtc)
    Pair(newIndex, acc.second && bool)
})

