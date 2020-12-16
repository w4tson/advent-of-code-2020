package day16

data class Rule(val name: String, val range1: IntRange, val range2 : IntRange) {
    fun matches(value: Int) : Boolean = (value in range1) or (value in range2)
    fun matches(t: Ticket) : Boolean = t.values.all(this::matches)
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

    fun matchingRules(value: Int) : List<Rule> = rules.filter { it.matches(value) }



    fun part2(tickets : List<Ticket>) : Int{
        val validTickets = validTickets(tickets)

        (validTickets[0].values.indices).map { i ->
            validTickets.map { t ->
                rules.count { it.matches(t.values[i]) }
            }.min()
        }.forEach { println(it) }

//        val min = validTickets.map { t ->
//            rules.count { it.matches(t.values[i]) }
//        }.forEach { println(it) }
//        println(min)


        return -1

    }

    fun foo(index: Int, tickets: List<Ticket>) : Pair<Int, Ticket> {
        when (tickets.size) {
            1 -> return Pair(index, tickets.first())
            else -> {
                (validTickets[0].values.indices).map { i ->
                    validTickets.map { t ->
                        rules.count { it.matches(t.values[i]) }
                    }.min()
                }.forEach { println(it) }
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