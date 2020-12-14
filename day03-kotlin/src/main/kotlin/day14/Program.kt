package day14


class Program(val instructions: List<Expr>) {
    var mask = Mask("")
    var mem = mutableMapOf<Long, Long>()
    
    operator fun invoke() : Long {
        instructions.forEach { 
            when(it) {
                is Mask -> mask = it
                is Assign -> assign(it)
            }
        }

        println(mem)
        return mem.values.sum()
    }
    
    fun runV2() : Long {
        instructions.forEach {
            when(it) {
                is Mask -> mask = it
                is Assign -> assignWithFloating(it)
            }
        }

        return mem.values.sum()
    }
    
    fun assign(assign : Assign) {
        mem.set(assign.address, UInt36(assign.decimalValue).applyMask(mask))
    }
    
    fun assignWithFloating(assign: Assign) {
        UInt36(assign.address).applyMask2(mask).forEach { 
            mem.set(it, assign.decimalValue)
        }
    }
}

