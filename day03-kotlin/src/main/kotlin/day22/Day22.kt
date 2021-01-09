package day22

import java.util.*

typealias Deck = Deque<Int>
data class GameState(val player1 :Deck, val player2: Deck, val turn: Int) {
    fun gameEnded() : Boolean = player1.size == 0 || player2.size == 0
    fun winner() : Deck = if (player1.size == 0) player2 else player1
}

fun genGame(initial: GameState) : Sequence<GameState> {
    return generateSequence(initial, { state ->
        val (player1, player2, turn) = state
//        if (state.gameEnded())  state

        val p1 = player1.poll()
        val p2 = player2.poll()

//        if (p1 == null || p2 == null) {
//            state
//        }
//        else

        if (p1 > p2) {
            player1.addLast(p1)
            player1.addLast(p2)
            GameState(player1, player2, turn + 1)
        } else {
            player2.addLast(p2)
            player2.addLast(p1)
            GameState(player1, player2, turn + 1)
        }
    })
}