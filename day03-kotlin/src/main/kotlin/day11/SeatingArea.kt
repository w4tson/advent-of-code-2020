package day11

import takeWhileInclusive

typealias SeatingMap = Array<Array<String>>
typealias Coord = Pair<Int, Int>

enum class Strategy {
    Nearest8,
    LineOfSight
}

class SeatingArea(val grid: SeatingMap, val nearbySeatThreshold: Int, val strategy : Strategy) {
    
    private var width : Int = grid[0].size
    private var height : Int = grid.size
    
    fun nextSq(x: Int, y: Int) : String {
        val square = grid[y][x]
        return  if (square == "L" &&  emptyInAllDirections(x,y))
            "#" 
        else if (square == "#" && tooManyNeighbours(x,y))
            "L"
        else
            square
    }
    
    fun lineOfSightSequence(init: Coord, vector: Coord) : Sequence<Coord> {
        return generateSequence(Coord(init.first + vector.first, init.second + vector.second),
                { (x ,y) -> Coord(x + vector.first, y + vector.second) })
    }
    
    
    
    
    fun emptyInAllDirections(x : Int, y: Int) : Boolean {
        return surroundingCoords(Coord(x,y)).count(this::occupiedAt) == 0
    }
    
    fun tooManyNeighbours(x : Int, y: Int) : Boolean {
        return surroundingCoords(Coord(x,y)).count(this::occupiedAt) > nearbySeatThreshold
    }

    fun next() : SeatingArea {
        return SeatingArea((0 until height).map{ y ->
            (0 until width).map{ x -> nextSq(x,y) }.toTypedArray()
        }.toTypedArray(), nearbySeatThreshold, strategy)
    }
    
    fun emptyAt(coord: Coord) : Boolean {
        return grid[coord.second][coord.first] != "#"
    }

    fun occupiedAt(coord: Coord) : Boolean {
        return grid[coord.second][coord.first] == "#"
    }
    
    fun surroundingCoords(pos : Coord) : List<Coord> {
  
        return listOf(
                Coord(0,-1),
                Coord(0,+1),
                Coord(-1,0),
                Coord(+1,0),
                Coord(+1,-1),
                Coord(+1,+1),
                Coord(-1,+1),
                Coord(-1,-1)
        )
        .map { lineOfSightSequence(pos, it) 
                .takeWhileInclusive{ (a,b) -> withinBounds(Coord(a,b)) && grid[b][a] == "." }
                .last()
        }
        .filter(this::withinBounds)
    }

    fun withinBounds(pos: Coord) : Boolean = pos.first in 0 until width && pos.second >= 0 && pos.second < height

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SeatingArea

        grid.forEachIndexed { i, row -> 
            if (!row.contentEquals(other.grid[i])) return false
        }

        return true
    }

    override fun hashCode(): Int {
        return grid.hashCode()
    }

    fun display() {
        grid.forEach {
            it.forEach {
                print(it)
            }
            println()
        }
        println()
    }

    fun numOccupied(): Int {
        return grid.sumBy { row -> row.sumBy { if (it == "#") 1 else 0} }
    }

}