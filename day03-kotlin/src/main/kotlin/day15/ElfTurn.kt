package day15




fun game2(seed: List<Int>, n : Int) : Int {
    val heard = mutableMapOf<Int, Int>()
    var curr= 0
    var index = 0
        
    while (index < n) {
        val item = curr
        index += 1
        
        if (index % 3000000 ==0 ){
            println(index)
        }

        val nextVal = when (index) {
            in 0..seed.size -> seed[index-1]
            else -> heard[item]?.let { index - it } ?: 0
        }
        heard[curr] = index         
        curr = nextVal 

    }
    return curr
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
    
    