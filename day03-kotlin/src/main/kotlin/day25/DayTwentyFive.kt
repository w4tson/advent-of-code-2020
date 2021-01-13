package day25

import java.math.BigInteger

fun transform(subject: Long, loopSize : Long) : Long {
    var value =1L
    (1..loopSize).forEach { 
        value *= subject
        value %= 20201227
    }
    return value
}

val m = BigInteger.valueOf(20201227)
val seven = BigInteger.valueOf(7)

fun calcLoops(hash: Long) : Long? {
    val hashBig = BigInteger.valueOf(hash)
    return (1L..Long.MAX_VALUE).find { e -> seven.modPow(BigInteger.valueOf(e), m) == hashBig }
}

fun part1(cardPublicKey: Long, doorPublicKey: Long): Long {
    val doorLoopSize = calcLoops(doorPublicKey)
    return transform(cardPublicKey, doorLoopSize!!)
}