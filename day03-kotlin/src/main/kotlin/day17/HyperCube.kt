package day17


data class HCoord(val x : Int, val y: Int, val z : Int, val w: Int)  {
    fun surrounding() : List<HCoord> {
        return (w-1..w+1).flatMap { ww ->
            (z-1..z+1).flatMap { zz ->
                (y - 1..y + 1).flatMap { yy ->
                    (x - 1..x + 1).map { xx ->
                        HCoord(xx, yy, zz, ww)
                    }
                }
            }
        }.filter { it != this }
    }
}

fun toHyperCube(init : String) : HyperCube {
    val m = init.lines().mapIndexed { y, s ->
        s.trim().toCharArray().mapIndexed { x, c -> when(c) {
            '#' -> Pair(HCoord(x, y, 0, 0), true)
            else -> Pair(HCoord(x, y, 0, 0), false)
        }}
    }.flatten().toMap()


    return HyperCube(m)
}

class HyperCube(val map: Map<HCoord, Boolean>) {

    fun next() : HyperCube {
        val minX = map.keys.minBy { it.x }!!.x - 2
        val maxX = map.keys.maxBy { it.x }!!.x + 2
        val minY = map.keys.minBy { it.y }!!.y - 2
        val maxY = map.keys.maxBy { it.y }!!.y + 2
        val minZ = map.keys.minBy { it.z }!!.z - 2
        val maxZ = map.keys.maxBy { it.z }!!.z + 2
        val minW = map.keys.minBy { it.w }!!.w - 2
        val maxW = map.keys.maxBy { it.w }!!.w + 2

        val newMap = (minW..maxW).flatMap { ww ->
            (minZ..maxZ).flatMap { zz ->
                (minY..maxY).flatMap { yy ->
                    (minX..maxX).map { xx ->
                        val point = HCoord(xx, yy, zz, ww)
                        when (map.getOrDefault(point, false)) {
                            true -> Pair(point, has2Or3ActiveNeighbours(point))
                            else -> Pair(point, has3ActiveNeighbours(point))
                        }
                    }
                }
            }
        }.toMap()

        return HyperCube(newMap)
    }

    fun has2Or3ActiveNeighbours(coord: HCoord) : Boolean {
        val b = coord.surrounding().count { map.getOrDefault(it, false) }
        return b == 2 || b == 3
    }

    fun has3ActiveNeighbours(coord: HCoord) : Boolean =
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
                print(when (map.getOrDefault(HCoord(xx, yy, z, 0), false)) {
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
