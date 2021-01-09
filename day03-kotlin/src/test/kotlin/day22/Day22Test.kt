package day22

import Util.Companion.readInput
import org.junit.Test
import takeWhileInclusive
import java.util.*

class Day22Test {
    
    fun strToPlayer(p : String) : Deque<Int> {
        val cards = ArrayDeque<Int>()
        p.lines().drop(1).map { it.toInt() }.forEach { 
            cards.addLast(it)
        }
        return cards
    } 
    
    @Test
    fun part1() {
        val initState = gameState()
        val postGame = genGame(initState).dropWhile { !it.gameEnded() }.take(1).last()
        println("postGame = $postGame")
        val winner = postGame.winner()
        val x = winner.zip((winner.size) downTo 1) { a, b ->
            println("$a * $b")    
            a * b 
        }.sum()
        println(x)
    }

    private fun gameState(): GameState {
        val input = readInput("/day22/day22.txt")
        val (p1, p2) = input.split("\n\n")
        return GameState(strToPlayer(p1), strToPlayer(p2), 0)
    }
}