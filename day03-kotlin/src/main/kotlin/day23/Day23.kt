package day23

import cycle
import takeWhileInclusive

typealias Cups = List<Int>

data class State(val cups : Cups, val current : Int) {
}

fun part1Seq(initialCups : Cups) : Sequence<State> {
    return generateSequence(State(initialCups, initialCups[0]), { state ->
        val (cups, current) = state
        val pickedUp = cups.cycle().dropWhile{ it != current}.drop(1).take(3).toList()
        val destLabel = getDestLabel(cups, current - 1, pickedUp)
        val indexOfCurrent = cups.indexOf(current)
        
        val r1 = cups
            .takeWhileInclusive { it != current }
            .toList()
            // 3 2 5 (8) 9 1 3 4 6 7
        val r2 = cups.cycle().drop(1 + indexOfCurrent + 3).takeWhileInclusive { it != destLabel }.toList()
            //  r1 slice to current
            //  r2 slice to dest
            //  pickedUp
            //  r3 remainder from destination 
        val r3 = cups.cycle().drop(cups.indexOf(destLabel) + 1 ).takeWhile { it != current }
//        val r4 = cups.cycle().drop(partialSize).take(Math.max(0,cups.size - partialSize))
        
        val newCups = r1.filter { it !in r2 && it !in pickedUp } + r2 + pickedUp + r3.filter { it !in r1 }
        val newCurrent = newCups.dropWhile { it != current }.take(2).last()

        println("cups: ${showCups(cups, current)}")
        println("pick up: ${pickedUp}")
        println("destination: ${destLabel}")
        println()
        println(r1)
        println(r2)
        println(pickedUp)
        println(r3.filter { it !in r1 }.toList())
        println()
        
//        newCups.takeWhile { it != current }
        // original index == 2
        // new index = 1
        // 8 
        val offset = cups.size - (indexOfCurrent - newCups.indexOf(current))
        val newCups2 = newCups.cycle().drop(offset).take(cups.size).toList()
        
        
        State(newCups2, newCurrent)
    }).drop(1)
}

fun part1(cups: Cups, n : Int) : String {
    val res = part1Seq(cups).take(n).last().cups
    return res.cycle().dropWhile { it !=1 }.take(9).joinToString(separator = "").drop(1)
}



// -- move 1 --
// cups: (3) 8  9  1  2  5  4  6  7
// pick up: 8, 9, 1
// destination: 2
// 
// -- move 2 --
// cups:  3 (2) 8  9  1  5  4  6  7
// pick up: 8, 9, 1
// destination: 7
// 
// -- move 3 --
// cups:  3  2 (5) 4  6  7  8  9  1
// pick up: 4, 6, 7
// destination: 3

// 3 2 (5) 4 6 7 8 9 1
// 2 (5) 8 9 1 3 4 6 7
// 8 9 1 3 
// 2 (5) 8 9 1 3 4 6 7 


//-- move 4 --
//cups:  7  2  5 (8) 9  1  3  4  6
//pick up: 9, 1, 3
//destination: 7




//
//-- move 5 --
//cups:  3  2  5  8 (4) 6  7  9  1
//pick up: 6, 7, 9
//destination: 3
//
//-- move 6 --
//cups:  9  2  5  8  4 (1) 3  6  7
//pick up: 3, 6, 7
//destination: 9

// 7 2 5 8 4 1 (9) 3 6

//-- move 8 --
//cups:  8  3  6  7  4  1  9 (2) 5
//pick up: 5, 8, 3
//destination: 1
//
//-- move 9 --
//cups:  7  4  1  5  8  3  9  2 (6)
//pick up: 7, 4, 1
//destination: 5
//
//-- move 10 --
//cups: (5) 7  4  1  8  3  9  2  6
//pick up: 7, 4, 1
//destination: 3



fun getDestLabel(cups: Cups, n : Int, pickedUp: Cups) : Int {
    return if (n < cups.min()!!)  {
        getDestLabel(cups, cups.max()!!, pickedUp)
    } else if (n in pickedUp) {
        getDestLabel(cups, n-1, pickedUp)
    } else {
        n
    }
}

fun showCups(cups: Cups, current : Int) : String {
    return cups.map { if (it == current) "($it)" else "$it" }.joinToString(separator = " ")
}

