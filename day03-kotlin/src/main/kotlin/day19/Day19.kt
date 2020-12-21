package day19


data class RuleMatch(val index1: Int, val index2: Int)

sealed class Rule(val index: Int)

data class Value(val id: Int, val value : String) : Rule(id) 
data class EitherMatch(val id: Int, val r1 : RuleMatch, val r2: RuleMatch) : Rule(id)
data class SingleEitherMatch(val id: Int, val v1 : Int, val v2: Int) : Rule(id)
data class DoubleMatch(val id: Int, val r1 : RuleMatch) : Rule(id)
data class SingleMatch(val id: Int, val v1: Int)  : Rule(id)



fun buildRegex(rules : Map<Int, Rule>) : String {
    return _buildRegex(0, rules, "")
}

fun _buildRegex(i: Int, rules : Map<Int, Rule>, acc : String) : String {
    return when (val rule = rules.get(i)!!) {
        is Value -> rule.value
        is DoubleMatch -> acc + _buildRegex(rule.r1.index1, rules, acc) + _buildRegex(rule.r1.index2, rules, acc)
        is SingleMatch -> acc + _buildRegex(rule.v1, rules, acc)
        is SingleEitherMatch -> {
            acc + "(" + _buildRegex(rule.v1, rules, acc) + "|" + _buildRegex(rule.v2, rules, acc) + ")"
        }
        is EitherMatch -> {
            acc + "(" + _buildRegex(rule.r1.index1, rules, acc) + _buildRegex(rule.r1.index2, rules, acc) + 
                    "|" +
                        _buildRegex(rule.r2.index1, rules, acc) + _buildRegex(rule.r2.index2, rules, acc) +
                    ")"
        }
    }
}