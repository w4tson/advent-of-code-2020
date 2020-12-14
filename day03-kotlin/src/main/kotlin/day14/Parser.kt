package day14

import java.lang.IllegalStateException

val r = Regex("mem\\[(\\d+)] = (\\d+)")


sealed class Expr
data class Mask(val value: String) : Expr()
data class Assign(val address: Long, val decimalValue: Long) : Expr() {
    
}



data class UInt36(val decimal: Long) {
    
    fun toDecimal(s :String) : Long {
        var m = 0L
        for (i in 0 until 36) {
            if (s[i] == '1') {
                m += 1L shl 35 - i
            }
        }
        return m
    }
    
    fun toBinary(): String {
        val result = StringBuilder()
        for (i in 36 - 1 downTo 0) {
            val mask : Long = 1L shl i
            result.append(if (decimal and mask != 0L) 1 else 0)
        }
        return result.toString()
    }

    fun applyMask(m : Mask) : Long {
        val newVal = UInt36(decimal).toBinary().zip(m.value)
            .map { (bValue, mValue) ->
                when (mValue) {
                    'X' -> bValue
                    '1' -> '1'
                    '0' -> '0'
                    else -> throw IllegalStateException("boom")
                }
            }.joinToString("")

        return toDecimal(newVal)
    }
    
    fun applyMask2(m: Mask) : List<Long> {
        return UInt36(decimal).toBinary().zip(m.value)
            .fold(listOf(""), { acc, item ->
                when (item.second) {
                    'X' -> acc.flatMap { listOf(it + '0', it + '1') }
                    '1' -> acc.map { it + '1' }
                    '0' -> acc.map { it + item.first } 
                    else -> throw IllegalStateException("boom")
                }
            })
            .map { toDecimal(it) }
            .also { println(it) }
    }

    override fun toString(): String {
        return "UInt36($decimal) ${toBinary()}"
    }
}






fun parse(input: String) : List<Expr> = input.lines()
    .map { when (it.substring(0, 3)) {
        "mas" -> Mask(it.substring(7))
        "mem" -> toAssign(it)    
        else -> throw IllegalStateException("Unknown operation") 
    }}



fun toAssign(s: String) : Assign {
    val match = r.find(s)!!
    val (address, decimal) = match.destructured
    return Assign(address.toLong(), decimal.toLong())
}

