package day16

data class Rule(val name: String, val range1: IntRange, val range2 : IntRange) {
    fun satisfiedBy(value: Int) : Boolean = (value in range1) or (value in range2)
    fun satisfiedBy(t: Ticket) : Boolean = t.values.all(this::satisfiedBy)
}


data class Ticket(val values: List<Int>) {}




class Day16(val rules: List<Rule>){

    var position : MutableMap<Rule, Int> = mutableMapOf()

    fun errorRates(tickets : List<Ticket>) : Int = tickets.sumBy { errorRate(it) }
    fun validTickets(tickets : List<Ticket>) : List<Ticket> = tickets.filter { valid(it) }

    fun errorRate(ticket : Ticket) : Int {
        return ticket.values.filter { matchingRules(it).isEmpty() }.sum()
    }

    fun valid(ticket: Ticket) : Boolean {
        return ticket.values.all { matchingRules(it).isNotEmpty() }
    }

    fun matchingRules(value: Int) : List<Rule> = rules.filter { it.satisfiedBy(value) }



    fun part2(tickets : List<Ticket>) : Long {
        val validTickets = validTickets(tickets)
        val numOfFields = validTickets[0].values.size

        var res = Pair<List<Ticket>, Map<Rule, Int>>(listOf(), mapOf())
        (rules.indices).forEach {
            res = calculateFieldPositions(0, validTickets, rules, numOfFields, res.second, res.first)
            println(res.second.size)
        }

   

        val numbers = res.second.entries.filter { (k, v) -> k.name.startsWith("departure") }
            .map { (_, v) -> tickets.last().values[v].toLong() }

        println("numbers = $numbers")

        return numbers.reduce(Long::times)
    }

    fun calculateFieldPositions(index: Int, tickets: List<Ticket>, rules: List<Rule>, fieldNums : Int, acc: Map<Rule, Int>, ignoredTickets: List<Ticket>) : Pair<List<Ticket>, Map<Rule, Int>> {
        if (index in acc.values) return calculateFieldPositions(index + 1, tickets, rules, fieldNums, acc, ignoredTickets)
        when (acc.size == fieldNums || rules.isEmpty() || index == fieldNums) {
            true -> return Pair(ignoredTickets, acc)
            else -> {   
                val subRules = rules.filter { rule ->
                    tickets.filter { !ignoredTickets.contains(it) }
                        .all {  t -> rule.satisfiedBy(t.values[index]) } and !acc.containsKey(rule)
                }
                
                if (subRules.size == 1) return Pair(ignoredTickets, mapOf(Pair(subRules[0], index)) + acc)
                
                val newIgnoredTickets = tickets.filter { t-> ignoredTickets.contains(t) or subRules.none { rule -> rule.satisfiedBy(t.values[index]) } }
                val newAcc = when (subRules.size) {
                    1 -> mapOf(Pair(subRules[0], index)) + acc
                    else -> acc
                }

                return calculateFieldPositions(index +1, tickets, subRules, fieldNums, newAcc, newIgnoredTickets)
            }
        }
    }

}


val r = Regex("([\\w ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)")

fun toRule(line: String) : Rule {
//    println(line)
    val match = r.find(line)!!
    val (name, from1, to1, from2, to2) = match.destructured
    return Rule(name, (from1.toInt()..to1.toInt()), (from2.toInt()..to2.toInt()))
}