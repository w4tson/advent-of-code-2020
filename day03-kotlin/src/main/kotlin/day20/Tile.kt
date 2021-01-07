package day20

import charList
import contentDeepEquals

val beasty = """
                  # 
#    ##    ##    ###
 #  #  #  #  #  #       
""".trimIndent()

val beastyLines = beasty.lines()

data class Tile(val id: Long, val data: List<List<Char>>, val size: Int) {

    fun show() : String {
        return data.map { it.joinToString(separator = "") }.joinToString(separator = "\n")
    }

    fun allPermutations() : List<Tile> = HashSet(
        Rotation.values().map { Tile(id, it.apply(data), size) } +
           Rotation.values().map { Tile(id, it.apply(this.flipX().data), size) } +
           Rotation.values().map { Tile(id, it.apply(this.flipY().data), size) }
    ).toList()

    fun leftEdge() : List<Char> = data.map { it[0] }

    fun rightEdge() : List<Char> = data.map { it[size-1] }

    fun topEdge() : List<Char> = data.first()

    fun bottomEdge() : List<Char> = data.last()

    fun linesUpWith(other: Tile) : Int {
        if (id == other.id) return 0
        return listOf(
            topEdge() == other.bottomEdge(),
            rightEdge() == other.leftEdge(),
            bottomEdge() == other.topEdge(),
            leftEdge() == other.rightEdge()
        ).count{ it }
    }

    fun flipX(): Tile {
        val flipped = data.indices.map { y->
            data.indices.map { x ->
                data[size-y-1][x]
            }
        }

        return Tile(id, flipped, size)
    }

    fun flipY() : Tile {
        val flipped = data.indices.map { y->
            data[y].reversed()
        }

        return Tile(id, flipped, size)
    }

    fun trimBorder() : Tile {
        return Tile(id, data.drop(1).dropLast(1).map { it.drop(1).dropLast(1) }, size-2)
    }

    fun countBeasties(): Int {
        return data.windowed(3).mapIndexed { i, (line1, line2, line3) ->
            val c1 = line1.windowed(20)
            val c2 = line2.windowed(20)
            val c3 = line3.windowed(20)

            c1.zip(c2).zip(c3) { (a, b), c ->
                a.zip(beastyLines[0].charList(), ::charMatch).sum() == 1 &&
                b.zip(beastyLines[1].charList(), ::charMatch).sum() == 8 &&
                c.zip(beastyLines[2].charList(), ::charMatch).sum() == 6
            }.count { it }
        }.sum()
    }

    fun waterRoughness() : Int = numberOfHashes() - (beastyCount() * 15)
    
    private fun beastyCount() = allPermutations().map { it.countBeasties() }.sum()
    
    private fun numberOfHashes() = data.sumBy { t -> t.count { it == '#' } }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tile

        if (id != other.id) return false
        if (data != other.data && !data.contentDeepEquals(other.data)) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + data.hashCode()
        result = 31 * result + size
        return result
    }

    companion object {
        fun toTile(str: String) : Tile {
            val lines = str.lines()
            val tileId = lines[0].split(" ")[1].dropLast(1).toLong()
            val size = lines.drop(1).size
            val data: List<List<Char>> = lines.drop(1).map{ it.toCharArray().toList() }
            return Tile(tileId, data, size)
        }
    }

}
