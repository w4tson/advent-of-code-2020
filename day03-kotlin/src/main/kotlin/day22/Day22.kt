package day22

import day22.Player.*
import toDeque
import java.util.*

typealias Deck = Deque<Int>

enum class Player {
    P1,
    P2
}

data class GameState(val player1 :Deck, val player2: Deck, val turn: Int, val winner: Player?) {
    fun gameEnded() : Boolean = winner!= null || player1.size == 0 || player2.size == 0
    fun winner() : Pair<Deck, Player> = if (winner != null) Pair(player1, winner) else if (player1.size == 0) Pair(player2, P2) else Pair(player1, P1)
}

fun runGame(initState: GameState) : Pair<Deck, Player> {
    return genGame(initState).dropWhile { !it.gameEnded() }.take(1).last().winner()
}

fun genGame(initial: GameState) : Sequence<GameState> {
    val p1Seen = mutableMapOf<String, Int>()
    val p2Seen = mutableMapOf<String, Int>()
    
    return sequence {
        yield(initial)
        var curr = initial
        do {
            val (player1, player2, turn, winner) = curr
            println("-- Round ${turn+1} () --")
            println("Player 1's deck: ${player1.joinToString(separator = ", ")}")
            println("Player 2's deck: ${player2.joinToString(separator = ", ")}")
            println("Player 1 plays: ${player1.first()}")
            println("Player 2 plays: ${player2.first()}")
            
            if (p1Seen.getOrDefault(player1.toString(), 0) > 1 || p2Seen.getOrDefault(player2.toString(), 0) > 1) {
                yield(GameState(player1, player2, turn + 1, P1))
            } else {
                p1Seen.compute(player1.toString()) { _, v -> if (v == null) 1 else v + 1 }
                p2Seen.compute(player2.toString()) { _, v -> if (v == null) 1 else v + 1 }

                val p1 = player1.poll()
                val p2 = player2.poll()

                val winnerOfSubGame = if (p1 <=player1.size && p2 <= player2.size)  {
                println("Playing a sub-game to determine the winner...")

                    val (_, player) = runGame(
                        GameState(
                            player1.take(p1).toDeque(),
                            player2.take(p2).toDeque(),
                            0,
                            null
                        )
                    )
                println("The winner of game is player ${player.name}")
                    player
                } else null

                println()

                if ((p1 > p2 && winnerOfSubGame == null ) || winnerOfSubGame == P1) {
                    player1.addLast(p1)
                    player1.addLast(p2)
                    curr = GameState(player1, player2, turn + 1, winner)
                    yield(curr)
                } else {
                    player2.addLast(p2)
                    player2.addLast(p1)
                    curr = GameState(player1, player2, turn + 1, winner)
                    yield(curr)
                }
            }
            
        } while(true)
    }
}

fun winningScore(deck : Deck) : Int = deck.zip((deck.size) downTo 1) { a, b -> a * b }.sum()