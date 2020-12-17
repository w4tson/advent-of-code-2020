package day17

data class Coord(val x : Int, val y: Int, val z : Int)  {
    fun surrounding() : List<Coord> = (z-1..z+1).flatMap { zz ->
                (y - 1..y + 1).flatMap { yy ->
                    (x - 1..x + 1).map { xx ->
                        Coord(xx, yy, zz) 
                    }
                }
            }.filter { it != this }
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
        val (minX,maxX,minY,maxY,minZ,maxZ) = minMax(gutter = 2);

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
    
    fun has2Or3ActiveNeighbours(coord: Coord) : Boolean =
        coord.surrounding().count { map.getOrDefault(it, false) } in 2..3

    fun has3ActiveNeighbours(coord: Coord) : Boolean =
        coord.surrounding().count { map.getOrDefault(it, false) } == 3
    
    fun minMax(gutter : Int = 0) : List<Int>{
        return listOf(
            map.entries.filter { (_,v) -> v }.map{ it.key.x }.min()!! - gutter,
            map.entries.filter { (_,v) -> v }.map{ it.key.x }.max()!! + gutter,
            map.entries.filter { (_,v) -> v }.map{ it.key.y }.min()!! - gutter,
            map.entries.filter { (_,v) -> v }.map{ it.key.y }.max()!! + gutter,
            map.entries.filter { (_,v) -> v }.map{ it.key.z }.min()!! - gutter,
            map.entries.filter { (_,v) -> v }.map{ it.key.z }.max()!! + gutter
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
