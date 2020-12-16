package day16

data class Rule(val name: String, val range1: IntRange, val range2 : IntRange) {
    fun matches(value: Int) : Boolean = (value in range1) or (value in range2)
}


data class Ticket(val values: List<Int>) {}




class Day16(val rules: List<Rule>){

    fun errorRates(tickets : List<Ticket>) : Int = tickets.sumBy { errorRate(it) }

    fun errorRate(ticket : Ticket) : Int {
        return ticket.values.filter { matchingRules(it).isEmpty() }.sum()
    }

    fun matchingRules(value: Int) : List<Rule> = rules.filter { it.matches(value) }

}


val r = Regex("([\\w ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)")

fun toRule(line: String) : Rule {
    println(line)
    val match = r.find(line)!!
    val (name, from1, to1, from2, to2) = match.destructured
    return Rule(name, (from1.toInt()..to1.toInt()), (from2.toInt()..to2.toInt()))
}