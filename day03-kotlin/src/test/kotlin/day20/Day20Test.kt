package day20

import Util.Companion.readInput
import charList
import org.junit.Test
import kotlin.test.assertEquals

class Day20Test {

    val exampleTiles = readInput("/day20/example.txt").split("\n\n").map { Tile.toTile(it) }
    val tiles = readInput("/day20/day20.txt").split("\n\n").map { Tile.toTile(it) }

    @Test
    fun examples() {
//        tiles.forEach { println(it.show() +"\n") }
        arrange(exampleTiles)
//        assertEquals(20899048083289, part1(exmapleTiles))
    }

    @Test
    fun tileTest() {
        val tile = exampleTiles[0]
        println(tile.show())
        println()
        val rotatedTile = Tile(tile.id,Rotation.R90.apply(tile.data), tile.size)
        println()
        println(tile.flipY().show())
        println()
        println(rotatedTile.show())
        println()
        println(tile.flipX().show())

        val flipped = tile.flipX().flipX()
        assertEquals(flipped, tile)
        println(tile.allPermutations().count())
        println(HashSet(tile.allPermutations()).count())
    }

    @Test
    fun part1() {
        part1(tiles).also { println(it) }
    }

    @Test
    fun testaPart2Example() {
        val fullImage = createFullImage(exampleTiles)
        assertEquals(2, fullImage.allPermutations().sumBy { it.countBeasties() })
        assertEquals(273, fullImage.waterRoughness())
    }

    @Test
    fun part2_() {
        println(createFullImage(tiles).waterRoughness())
    }
}

