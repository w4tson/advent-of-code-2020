package day17

data class Coord(val x : Int, val y: Int, val z : Int)  {
    fun surrounding() : List<Coord> {
        return (z-1..z+1).flatMap { zz ->
            (y - 1..y + 1).flatMap { yy ->
                (x - 1..x + 1).map { xx ->
                    Coord(xx, yy, zz) 
                }
            }
        }.filter { it != this }
    }
    
    
}

fun toPocketDimension(init : String) : PocketDimension {
    val m = init.lines().mapIndexed { y, s ->
        s.trim().toCharArray().mapIndexed { x, c -> when(c) {
            '#' -> Pair(Coord(x, y, 0), true)
            else -> Pair(Coord(x, y, 0), false)
        }}
    }.flatten().toMap()


    return PocketDimension(m)
}

class PocketDimension(val map: Map<Coord, Boolean>) {
    
    fun next() : PocketDimension {
        val minX = map.keys.minBy { it.x }!!.x - 2
        val maxX = map.keys.maxBy { it.x }!!.x + 2
        val minY = map.keys.minBy { it.y }!!.y - 2
        val maxY = map.keys.maxBy { it.y }!!.y + 2
        val minZ = map.keys.minBy { it.z }!!.z - 2
        val maxZ = map.keys.maxBy { it.z }!!.z + 2

        val newMap = (minZ..maxZ).flatMap { zz ->
            (minY..maxY).flatMap { yy ->
                (minX..maxX).map { xx ->
                    val point = Coord(xx, yy, zz)
                    when (map.getOrDefault(point,false)) {
                        true -> Pair(point, has2Or3ActiveNeighbours(point))
                        else -> Pair(point, has3ActiveNeighbours(point))
                    }
                }
            }
        }.toMap()
        
        return PocketDimension(newMap)
    }
    
    fun has2Or3ActiveNeighbours(coord: Coord) : Boolean {
        val b = coord.surrounding().count { map.getOrDefault(it, false) } 
        return b == 2 || b == 3
    }

    fun has3ActiveNeighbours(coord: Coord) : Boolean =
        coord.surrounding().count { map.getOrDefault(it, false) } == 3
    
    fun minMax() : List<Int>{
        return listOf(
            map.entries.filter { (_,v) -> v }.map{ it.key.x }.min()!!,
            map.entries.filter { (_,v) -> v }.map{ it.key.x }.max()!!,
            map.entries.filter { (_,v) -> v }.map{ it.key.y }.min()!!,
            map.entries.filter { (_,v) -> v }.map{ it.key.y }.max()!!,
            map.entries.filter { (_,v) -> v }.map{ it.key.z }.min()!!,
            map.entries.filter { (_,v) -> v }.map{ it.key.z }.max()!!
        )
    }
    
    
    fun show(z : Int) {
        println("z=$z")
        val (minX, maxX, minY, maxY, _, _) = minMax()
        (minY..maxY).forEach { yy ->
            (minX..maxX).forEach { xx ->
                print(when (map.getOrDefault(Coord(xx, yy, z), false)) {
                    true -> "#"
                    else -> "."
                })
            }
            println()
        }
        println()
        
    }
    
    
    
    
}

private operator fun <E> List<E>.component6(): E {
    return this.get(5)
}
