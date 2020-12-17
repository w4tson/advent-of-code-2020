package day16

data class Rule(val name: String, val range1: IntRange, val range2 : IntRange) {
    fun satisfiedBy(value: Int) : Boolean = (value in range1) or (value in range2)
    fun satisfiedBy(t: Ticket) : Boolean = t.values.all(this::satisfiedBy)
}


data class Ticket(val values: List<Int>) {}

data class State(val rules : Map<Rule, Int>, val tickets: List<Ticket>, val visitedTickets : List<Ticket>) {
    operator fun plus(anotherRule : Map<Rule, Int>) : State = State(rules + anotherRule, tickets, visitedTickets)
}

class Day16(val rules: List<Rule>){

    val numOfFields = rules.size

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

        var state = State(mapOf(), validTickets, listOf())
        (rules.indices).forEach { _ ->
            state = calculateFieldPositions(0, rules, state)
            println(state.rules.size)
        }

        return state.rules.entries.filter { (k, _) -> k.name.startsWith("departure") }
            .map { (_, v) -> tickets.last().values[v].toLong() }
            .reduce(Long::times)
    }

    private fun calculateFieldPositions(index: Int, rules: List<Rule>, state: State) : State {
        if (index in state.rules.values) return calculateFieldPositions(index + 1, rules, state)
        return when (state.rules.size == numOfFields || rules.isEmpty() || index == numOfFields) {
            true -> state
            else -> {
                val subRules = rules.filter { rule ->
                    state.tickets.filter { !state.visitedTickets.contains(it) }
                        .all {  t -> rule.satisfiedBy(t.values[index]) } and !state.rules.containsKey(rule)
                }
                val newVisitedTickets = state.tickets.filter { t-> state.visitedTickets.contains(t) or subRules.none { rule -> rule.satisfiedBy(t.values[index]) } }

                when (subRules.size) {
                    1 -> state + mapOf(Pair(subRules[0], index))
                    else -> calculateFieldPositions(index +1, subRules, State(state.rules, state.tickets, newVisitedTickets))
                }
            }
        }
    }
}


val r = Regex("([\\w ]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)")

fun toRule(line: String) : Rule {
    val match = r.find(line)!!
    val (name, from1, to1, from2, to2) = match.destructured
    return Rule(name, (from1.toInt()..to1.toInt()), (from2.toInt()..to2.toInt()))
}