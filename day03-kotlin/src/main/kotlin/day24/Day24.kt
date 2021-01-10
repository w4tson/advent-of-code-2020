package day24

import day24.Color.Black
import day24.Color.White

enum class HexDirection(val x : Int, val y : Int) {
    E(1,0),
    W(-1,0),
    NW(0,-1),
    NE(1,-1),
    SW(-1,1),
    SE(0,1);
}

enum class Color {
    Black,
    White;
    
    fun flip() : Color = if (this == Black) White else Black
}

fun part1(tiles : List<List<HexDirection>>) : Int = flipEm(tiles).values.count { it == Black }

private fun flipEm(tiles: List<List<HexDirection>>): MutableMap<Pair<Int, Int>, Color> {
    val m = mutableMapOf<Pair<Int, Int>, Color>()
    val coordList = tiles.map { toCoord(it) }

    coordList.forEach {
        m.compute(it) { _, c -> c?.flip() ?: Black }
    }
    return m
}

fun part2(tiles: List<List<HexDirection>>) : Sequence<Int> {
    val m = flipEm(tiles)
    return sequence {  
        do {
            m.entries
                .filter { it.value == Black }
                .flatMap { adjacentTilesAndItself(it.key) }.toSet()
                .filter {
                    val colors = adjacentColors(it, m)
                    val tileColor = m.getOrDefault(it, White)
                    (tileColor == Black && colors.count { it == Black } !in listOf(1,2))
                        || (tileColor == White && colors.count { it == Black } == 2)
                }.forEach { m.compute(it) { _, c -> c?.flip() ?: Black } }

            yield(m.values.count { it == Black })

        } while(true)
    }
}

fun adjacentTilesAndItself(tile: Pair<Int, Int>) : List<Pair<Int, Int>> = adjacentTiles(tile) + tile

fun adjacentTiles(tile: Pair<Int, Int>) : List<Pair<Int, Int>> =
    HexDirection.values().map { Pair(tile.first + it.x, tile.second + it.y) }

fun adjacentColors(tile: Pair<Int, Int>, floor: Map<Pair<Int, Int>, Color>) : List<Color> =
    adjacentTiles(tile).map { floor.getOrDefault(it, White) }

fun toCoord(directions: List<HexDirection>) : Pair<Int, Int> {
    return directions.fold(Pair(0,0), { (x,y), item -> 
        Pair(x + item.x, y + item.y)
    })
}