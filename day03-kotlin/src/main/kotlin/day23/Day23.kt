package day23

import cycle
import multiply

typealias Cups = CircularList<Int>

data class State(val cups : Cups, val current : Item<Int>) 

fun part1(cups: Cups, n : Int) : String {
    val finalState = cupGame(cups).take(n).last().cups.take(cups.size).toList()
    return finalState.cycle().dropWhile { it.data !=1 }.take(9).map{ it.data}.joinToString(separator = "").drop(1)
}

fun part2(cups: Cups) : Long {
    val finalState = cupGame(cups).take(10_000_000).last().cups.take(cups.size).toList()
    return finalState.cycle().dropWhile { it.data !=1 }.take(3).map { it.data.toLong() }.multiply()
}

fun cupGame(initial : Cups) : Sequence<State> {
    val list = initial.take(initial.size).toList()
    val min = list.minBy { it.data }!!.data
    val max = list.maxBy { it.data }!!.data
    val cache = list.associateBy { it.data }

    return generateSequence(State(initial, initial.first())) { (cups, current) ->
        val pickedUp = cups.cutNFrom(3, current)
        val destLabel = getDestLabel(min, max, current.data - 1, pickedUp)
        val destinationCup = cache.get(destLabel)!!
        cups.insertAt(destinationCup, pickedUp)
        State(cups, current.next!!)
    }.drop(1)
}

fun getDestLabel(min: Int, max: Int, n : Int, pickedUp: Cups) : Int {
    return when {
        n < min -> getDestLabel(min, max, max, pickedUp)
        pickedUp.contains(n) -> getDestLabel(min, max, n-1, pickedUp)
        else -> n
    }
}
