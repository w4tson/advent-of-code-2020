package day20

import multiply
import kotlin.math.sqrt

typealias Grid = List<List<Char>>

enum class Rotation {
    None,
    R90,
    R180,
    R270;
    
    fun apply(data : Grid) : Grid {
        return when (this) {
            None -> data
            R90 -> rotate90(data)
            R180 -> rotate90(rotate90(data))
            R270 -> rotate90(rotate90(rotate90(data)))
        }
    }

    private fun rotate90(data: Grid) : Grid = data.indices.map { y ->
        data.indices.map { x ->
            data[x][y]
        }.reversed()
    }
}


fun part1(tiles: List<Tile>) : Long {
    val corners = tiles.associateWith { t ->
        tiles.filter { it != t }.flatMap(Tile::allPermutations).map(t::linesUpWith).sum()
    }.filter { it.value == 2 }
    return corners.map { it.key.id }.multiply()
}

fun charMatch(a: Char, b : Char) : Int {
    return if (a == b) 1 else 0 
}

fun arrange(tiles: List<Tile>) : List<List<Tile>> {
    val graph: Map<Tile, List<Tile>> = tiles.flatMap { it.allPermutations() }.associateWith { t ->
        tiles.filter { it != t }.flatMap(Tile::allPermutations).filter { t.linesUpWith(it) > 0 }
    }

    val topLeft = graph.filter { it.value.size == 2 }.entries.find {
        it.value[0].leftEdge() == it.key.rightEdge() &&
                it.value[1].topEdge() == it.key.bottomEdge() 
    }?.key!!
    
    val size = sqrt(tiles.size.toDouble()).toInt()
    val image = mutableListOf<MutableList<Tile>>()
    var curr = topLeft
    for (i in 0 until size) {
        val newRow = mutableListOf(curr.trimBorder())
        val oneBelow = graph[curr]?.find { curr.bottomEdge() == it.topEdge() }
        for ( j in 1 until size) {
            graph[curr]?.find { curr.rightEdge() == it.leftEdge() && curr.id != it.id }?.let {
                newRow.add(it.trimBorder())
                curr = it
            }
            
        }
        oneBelow?.let { 
            curr = it
        }
        image.add(newRow)
    }
    
    return image
}

fun createFullImage(tiles: List<Tile>) : Tile {
    val image = arrange(tiles)
    val tileSize = image[0][0].data.size
    val fullSize = image.size * tileSize
    val bigImage = (0 until fullSize).map { y ->
        (0 until fullSize).map { x ->
            image[y / tileSize][x / tileSize].data[y % tileSize][x % tileSize]
        }
    }
    
    return Tile(1, bigImage, fullSize)
}