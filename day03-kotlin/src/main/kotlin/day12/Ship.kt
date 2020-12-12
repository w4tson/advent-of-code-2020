package day12

import day12.Inst.*
import kotlin.math.absoluteValue

enum class Inst {
    North,
    East,
    South,
    West,
    Right,
    Left,
    Forward;
    
    companion object {
        fun fromStr(s : String) = values().firstOrNull {it.name.startsWith(s)}
    }
}

enum class Direction{
    North,
    South,
    East,
    West
}

fun repeatRange(repeat : Int) : Sequence<Int> {
    return generateSequence(0, { (it+1) % repeat }) 
}

fun <T> List<T>.cycle() : Sequence<T> {
    return repeatRange(this.size).map { this[it] }
}

class Move(val inst : Inst, val value : Int){

}


class Location(val x : Int, val y : Int, val facing: Inst) {
    
    fun advance(move : Move) : Location {
        return when (move.inst) {
            North -> Location(x, y-move.value, facing)
            East -> Location(x+move.value, y, facing)
            South -> Location(x, y+move.value, facing)
            West -> Location(x-move.value, y, facing)
            Forward -> this.advance(Move(facing, move.value))
            Right -> rotateClocwise(move.value)
            Left -> rotateAntiClocwise(move.value)
        } 
    }
    
    fun face(direction: Inst) : Location = Location(x,y,direction)
    
    fun rotateClocwise(degress : Int) : Location {
        val directions = Inst.values().take(4)
        val newDirection = directions.cycle()
            .take(directions.indexOf(facing) +1 + degress / 90)
            .last()

        return Location(x,y, newDirection)
    }

    fun rotateAntiClocwise(degress : Int) : Location {
        val directions = Inst.values().take(4).reversed()
        val newDirection = directions.cycle()
            .take(directions.indexOf(facing) +1 + degress / 90)
            .last()

        return Location(x,y, newDirection)
    }

    override fun toString(): String {
        return "location(($x, $y), $facing)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Location

        if (x != other.x) return false
        if (y != other.y) return false
        if (facing != other.facing) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + facing.hashCode()
        return result
    }


}

class Ship(val location: Location) {
    fun move(move: Move) : Ship {
        return Ship(location.advance(move))
    }
    
    fun manhattenDist() : Int = location.x.absoluteValue + location.y.absoluteValue
    
    override fun toString(): String {
        return "Ship($location)"
    }
}

fun steer(moves : List<Move>) : Int {
    return moves.fold(Ship(Location(0,0, East)), { s, m ->
        val newShip = s.move(m)
        println(newShip)
        newShip 
    }).manhattenDist()
}