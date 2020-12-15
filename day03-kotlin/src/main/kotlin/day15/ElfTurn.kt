package day15

data class ElfTurn(var heard: MutableMap<Long,MutableList<Long>>, var curr: Long, var index :Long) {
}

fun game2(seed: List<Long>) : Sequence<ElfTurn> {
    var elf = ElfTurn(mutableMapOf(), 0, 0)
    return generateSequence(elf, { turn ->
        var turn = turn;
        val item = turn.curr
        val i = turn.index +1
        when (turn.index) {
            in seed.indices -> {
                turn.index += 1
                val newVal = seed[turn.index.toInt()]
                turn.heard[i]?.plusAssign(newVal)
                turn.curr = newVal
                turn
            }
            else -> {
                val next = if (turn.heard[item]?.size > 1) 0
                else {
                    prev.size - prev.dropLast(1).lastIndexOf(item) -1
                }
                turn
            }
        }
    })
}


fun game(seedList: List<Int>): Sequence<List<Int>> {
    val seed : List<List<Int>> = seedList.drop(1).fold(listOf(listOf(seedList.first())), { acc, it ->
        val next = if (acc.isNotEmpty()) {
            val l = acc.last().toMutableList()
            l.add(it)
            listOf(l)
        } else {
            emptyList()
        }

        acc + next
    })

    return generateSequence(listOf<Int>(), { prev ->
        when (prev.size) {
            in seedList.indices -> seed[prev.size]
            else -> {
                val item = prev.last()
                val next = if (prev.dropLast(1).count { it == item } < 1) 0
                else {
                    prev.size - prev.dropLast(1).lastIndexOf(item) -1
                }
                prev + next

            }
        }
    }).drop(1)
}
    
    