package day24

import Util.Companion.readInput
import charList
import day24.HexDirection.*
import org.junit.Assert.*
import org.junit.Test

class Day24Test {
    val exampleTiles = readInput("/day24/example.txt").lines().map(this::toDirections)
    val tiles = readInput("/day24/day24.txt").lines().map(this::toDirections)
    
    @Test
    fun example() {
        assertEquals(10, part1(exampleTiles))
    }

    @Test
    fun part1() {
        println(part1(tiles))
    }

    @Test
    fun part2() {
        val result = part2(tiles).take(100).last()
        println(result)
    }

    fun toDirections(s :String) : List<HexDirection> {
        return s.charList().fold(State(emptyList(), emptyList()), { s: State, c ->

            if (s.curr.isEmpty()) {
                if (c == 'e') {
                    State(s.directions + E, emptyList())
                } else if (c == 'w') {
                    State(s.directions + W, emptyList())
                } else {
                    State(s.directions, listOf(c))
                }
            } else if (s.curr.first() == 's'){
                if (c == 'e') {
                    State(s.directions + SE, emptyList())
                } else {
                    State(s.directions + SW, emptyList())
                }
            } else {
                if (c == 'e') {
                    State(s.directions + NE, emptyList())
                } else {
                    State(s.directions + NW, emptyList())
                }
            }
        }).directions
    }
}

data class State(val directions :List<HexDirection>, val curr: List<Char>)