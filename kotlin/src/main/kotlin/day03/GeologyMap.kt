package day03

import java.nio.charset.StandardCharsets

data class GeologyMap(val input: String) {
    
    private val map = input.replace("\n","",true)
    val width: Int = input.lines()[0].length
    val height: Int = input.lines().size

    fun getGeologyAt(x: Int, y: Int): Char {
        return map[(y * (width)) + (x % (width))] 
    }
}

object  Part01 {

    fun readInput(fileName: String): String
            = this::class.java.getResource(fileName).readText(StandardCharsets.UTF_8)
    
    fun solve(inputFileName: String, slope: Pair<Int,Int>): Int {
        val map = GeologyMap(readInput(inputFileName))

        val seq = generateSequence(slope, { 
           Pair(it.first+slope.first, it.second+slope.second)
        })

        val hops = (map.height -1)/slope.second

        return seq.take(hops)
                .map {  map.getGeologyAt(it.first, it.second) }
                .filter { it == '#' }
                .count()
        
    }
}