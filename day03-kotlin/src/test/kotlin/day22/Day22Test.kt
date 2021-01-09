package day22

import Util.Companion.readInput
import org.junit.Test
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
        val (winner) = runGame(initState)
       
        val x = winner.zip((winner.size) downTo 1) { a, b ->
            println("$a * $b")    
            a * b 
        }.sum()
        println(x)
    }

    @Test
    fun part2() {
        val initState = gameState()
        val (winner) = runGame(initState)
        winningScore(winner).also { println(it) }
    }

    @Test
    fun part2Example() {
        val initState = gameState("/day22/example.txt")
        val (winner) = runGame(initState)

        winningScore(winner).also { println(it) }
        
    }

    @Test
    fun name() {
        
        val a = ArrayDeque<Int>()
        a.push(1)
        a.push(2)
        a.push(3)
        println(a.toString())
    }

    private fun gameState(file : String = "/day22/day22.txt"): GameState {
        val input = readInput(file)
        val (p1, p2) = input.split("\n\n")
        return GameState(strToPlayer(p1), strToPlayer(p2), 0, null)
    }
}