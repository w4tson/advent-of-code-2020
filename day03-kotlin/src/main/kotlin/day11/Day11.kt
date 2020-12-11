package day11.day11

typealias SeatingMap = List<MutableList<String>>
typealias Coord = Pair<Int, Int>

class Day11(val seatingMap: SeatingMap) {
    
    private var width : Int
    private var height : Int
    
    init {
        width = seatingMap[0].size
        height = seatingMap.size
    }
    
    
    
    fun occupyAll() : SeatingMap {
        return (0 until height).map{ x ->
            (0 until width).map{ y -> occupy(x,y) }.toMutableList()
        }.toList()
    }
    
    fun evictAll() : SeatingMap {
        return (0 until height).map{ x ->
            (0 until width).map{ y -> evict(x,y) }.toMutableList()
        }.toList()
    }
    
    fun occupy(x : Int, y: Int) : String  {
        return if (seatingMap[x][y] != "." && surroundingCoords(Coord(x,y)).all(this::emptyAt)) 
            "#" 
        else 
            seatingMap[x][y]
    }

    fun evict(x : Int, y: Int) : String  {
        return if (seatingMap[x][y] != "." && surroundingCoords(Coord(x,y)).count { occupiedAt(it) } > 3) 
            "L"
        else
            seatingMap[x][y]
    }
    
    fun emptyAt(coord: Coord) : Boolean {
        return seatingMap[coord.first][coord.second] != "#"
    }

    fun occupiedAt(coord: Coord) : Boolean {
        return seatingMap[coord.first][coord.second] == "#"
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
    
    fun withinBounds(pos: Coord) : Boolean = pos.first < width && pos.first >= 0 && pos.second >= 0 && pos.second < height

    fun List<MutableList<String>>.display() {
        this.forEach {
            it.forEach {
                print(it)
            }
            println()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Day11

        if (seatingMap!= other.seatingMap) return false

        return true
    }

    override fun hashCode(): Int {
        return seatingMap.hashCode()
    }


}