package day11

typealias SeatingMap = Array<Array<String>>
typealias Coord = Pair<Int, Int>

class SeatingArea(val grid: SeatingMap) {
    
    private var width : Int = grid[0].size
    private var height : Int = grid.size
    
    fun nextSq(x: Int, y: Int) : String {
        val square = grid[y][x]
        return  if (square == "L" && surroundingCoords(Coord(x,y)).all(this::emptyAt)) 
            "#" 
        else if (square == "#" && surroundingCoords(Coord(x,y)).count { occupiedAt(it) } > 3)
            "L"
        else
            square
    }

    fun next() : SeatingArea {
        return SeatingArea((0 until height).map{ y ->
            (0 until width).map{ x -> nextSq(x,y) }.toTypedArray()
        }.toTypedArray())
    }
    
    fun emptyAt(coord: Coord) : Boolean {
        return grid[coord.second][coord.first] != "#"
    }

    fun occupiedAt(coord: Coord) : Boolean {
        return grid[coord.second][coord.first] == "#"
    }
    
    fun surroundingCoords(pos : Coord) : List<Coord> {
        val x =  pos.first
        val y = pos.second
        return listOf(
                Coord(x,y-1),
                Coord(x,y+1),
                Coord(x-1,y),
                Coord(x+1,y),
                Coord(x+1,y-1),
                Coord(x+1,y+1),
                Coord(x-1,y+1),
                Coord(x-1,y-1)
        ).filter(this::withinBounds)
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