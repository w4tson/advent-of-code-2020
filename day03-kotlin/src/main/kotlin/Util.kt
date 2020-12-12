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

