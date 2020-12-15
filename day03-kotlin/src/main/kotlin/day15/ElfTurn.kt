package day15

data class ElfTurn(val heard: Map<Int,Int>, val curr: Int, val index :Int) {
   
        
//        fun init(i : Int): ElfGame {
//            println(i)
//            previous = i
//            heard[index] = i
//            index +=1
//            return this
//        }
//    
//        fun next() : ElfGame {
//            
//            // consider previous
//            // map(num, last spoken)
//            
//           val newNum = heard.get(previous)?.let { index - it } ?: 0
//           previous = newNum
//           heard[newNum] = index 
//            index +=1
//            
//            
//            return this
//        }




}


fun game(startingSeq: List<Int>): Sequence<ElfTurn> {
    println("asdf")
    val turn1 = ElfTurn(mapOf(), startingSeq[0], 1) 
    val s = startingSeq.drop(1).fold(listOf<ElfTurn>(turn1), { acc, it ->
        val prev = acc.last()
        val index = prev.index + 1
        val heard = prev.heard + Pair(it, index)
        acc + ElfTurn(heard, it, index)
    })

    println(s.dropLast(1).asSequence().toList())
    println(s.last())
    
//    return s.asSequence()
    val sequence = s.dropLast(1).asSequence() + generateSequence(s.last(), { e ->
        val i = e.index + 1
        val newNum = e.heard[e.curr]?.let {
            i - it
        } ?: 0

        ElfTurn(e.heard + Pair(newNum, i), newNum, i)
    })
    println()
    println()
    println()
    return sequence
}
    
    