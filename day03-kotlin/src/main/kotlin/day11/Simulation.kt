package day11

class Simulation {
    
    fun solve(seatingArea: SeatingArea) : Int{
        return generateSequence(seatingArea, { it.next() })
                .zipWithNext()
                .takeWhile { (a, b) ->  a != b }
                .last().let { (a, _) -> a.next().numOccupied() }
    }
}