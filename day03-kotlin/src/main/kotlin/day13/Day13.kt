package day13

import naturalNumbers

fun part01(arrival: Int, timetable: List<Int>) : Int {
    val buses = naturalNumbers().map { n ->
        timetable.map { if (n % it == 0) "D" else "."  }
    }

    val waitingTime = buses.drop(arrival).indexOfFirst { l -> l.any { it == "D" } }.also { println(it) }
    val busId = timetable[buses.elementAt(arrival + waitingTime).indexOf("D")]

    return waitingTime * busId
}


fun part02(inputs : List<Long>) : Long {
    val N = inputs.map{ if (it ==0L) 1 else it }.reduce( { a,b -> a * b})

    val x = (inputs.indices)
        .filter { inputs[it] != 0L }
        .map {
            val a = if (it == 0) 0 else inputs[it] - it
            val y = N / inputs[it]
            y * a * modInverse(y, inputs[it])
        }
        .also { println(it) }
        .sum()

    return x % N
}

fun modInverse(a: Long, m: Long): Long {
    if (a ==0L) return 0L
    var a = a
    a %= m
    for (x in 1 until m) if (a * x % m == 1L) return x
    return 1
} 