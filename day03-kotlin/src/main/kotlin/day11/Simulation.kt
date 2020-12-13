package day11

import takeWhileInclusive

class Simulation {
    
    fun solve(seatingArea: SeatingArea) : Int{
        return generateSequence(seatingArea, { it.next() })
                .zipWithNext()
                .takeWhileInclusive { (a, b) ->  a != b }
                .last().let { (a, _) -> a.numOccupied() }
    }
}

