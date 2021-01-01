import java.nio.charset.StandardCharsets

class Util {
    
    companion object {
        fun readInput(fileName: String): String =
            this::class.java.getResource(fileName).readText(StandardCharsets.UTF_8)
    }
}

fun repeatRange(repeat : Int) : Sequence<Int> {
    return generateSequence(0, { (it+1) % repeat })
}

fun <T> List<T>.cycle() : Sequence<T> {
    return repeatRange(this.size).map { this[it] }
}

fun naturalNumbers() : Sequence<Int> = (0..Int.MAX_VALUE).asSequence()

fun <T> Sequence<T>.takeWhileInclusive(predicate : (T) -> Boolean) : Sequence<T> {
    var keepGoing: Boolean
    var prevPred = true
    return this.takeWhile{
        keepGoing = prevPred
        prevPred = predicate(it)
        keepGoing
    }
}

fun <T> List<T>.takeWhileInclusive(predicate : (T) -> Boolean) : List<T> {
    var keepGoing: Boolean
    var prevPred = true
    return this.takeWhile{
        keepGoing = prevPred
        prevPred = predicate(it)
        keepGoing
    }
}

fun Sequence<Long>.multiply() : Long = this.reduce{ acc, i -> acc * i} 
