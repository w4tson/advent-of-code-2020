package day11

class Simulation {
    
    fun solve(seatingArea: SeatingArea) : Int{
        return generateSequence(seatingArea, { it.next() })
                .zipWithNext()
                .takeWhileInclusive { (a, b) ->  a != b }
                .last().let { (a, _) -> a.numOccupied() }
    }
}

fun <T> Sequence<T>.takeWhileInclusive(predicate : (T) -> Boolean) : Sequence<T> {
    var keepGoing: Boolean
    var prevPred = true
    return this.takeWhile{
        keepGoing = prevPred  
        prevPred = predicate(it)
        keepGoing
    }
}