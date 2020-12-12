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

fun repeatRange(repeat : Int) : Sequence<Int> {
    return generateSequence(0, { (it+1) % repeat }) 
}

fun <T> List<T>.cycle() : Sequence<T> {
    return repeatRange(this.size).map { this[it] }
}

data class Move(val inst : Inst, val value : Int)


class Location(val x : Int, val y : Int, val facing: Inst) {
    
    fun advanceWaypoint(move: Move, about: Location) : Location {
        return when (move.inst) {
            Left -> rotateAntiClockwiseAbout(move, about)
            Right -> rotateClockwiseAbout(move, about)
            else -> advance(move)
        }
    }
    
    fun rotateClockwiseAbout(move: Move, about: Location) : Location {
        val cx = x-about.x
        val cy = y-about.y

        val r1 = Location(about.x + (-1 * cy), about.y + cx, facing)
        return when (move.value / 90) {
            1 -> r1
            else -> r1.rotateClockwiseAbout(Move(move.inst, move.value - 90), about)
        }
    }

    fun rotateAntiClockwiseAbout(move: Move, about: Location) : Location {
        val cx = x-about.x
        val cy = y-about.y
        val r1 = Location(about.x + cy, about.y + (-1 * cx), facing)

        return when (move.value / 90) {
            1 -> r1
            else -> r1.rotateAntiClockwiseAbout(Move(move.inst, move.value - 90), about)
        }
    }
    
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
    
    fun advanceBy(l: Location, magnitude: Int) : Location {
        return Location(
            x + (magnitude * l.x ), 
            y + (magnitude * l.y ), 
            facing
        )  
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

    operator fun minus(increment: Location): Location {
        return Location(x - increment.x, y- increment.y, facing)
    }


}




class Ship(val location: Location, val waypoint: Location) {
    fun move(move: Move) : Ship {
        return if (move.inst == Forward) {
            val relativeWaypoint = waypoint - location
            val newLoc = location.advanceBy(relativeWaypoint, move.value)
            Ship(newLoc, newLoc.advanceBy(relativeWaypoint, 1))
        } else {
            Ship(location, waypoint.advanceWaypoint(move, location))
        }
    }
    
    fun manhattenDist() : Int = location.x.absoluteValue + location.y.absoluteValue
    
    override fun toString(): String {
        return "Ship($location w=$waypoint)"
    }
}

fun steer(moves : List<Move>) : Int {
    return moves.fold(Ship(Location(0,0, East), Location(10, -1, North)), { s, m ->
        val newShip = s.move(m)
        println(newShip)
        newShip 
    }).manhattenDist()
}   