package day12

import cycle
import day12.Inst.*
import day12.Rotation.AntiClockwise
import day12.Rotation.Clockwise
import kotlin.math.absoluteValue

val compassDirections = Inst.values().take(4)

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

enum class Rotation(val xsign: Int, val ysign: Int) {
    Clockwise(-1, 1),
    AntiClockwise(1, -1)
}

data class Move(val inst : Inst, val value : Int)

data class Location(val x : Int, val y : Int, val facing: Inst) {
    
    fun advanceWaypoint(move: Move, about: Location) : Location = when (move.inst) {
        Left -> rotateAntiClockwiseAbout(move, about)
        Right -> rotateClockwiseAbout(move, about)
        else -> advance(move)
    }

    fun rotate(move: Move, about: Location, r: Rotation) : Location {
        val cx = x-about.x
        val cy = y-about.y

        val r1 = Location(about.x + (r.xsign * cy), about.y + (r.ysign * cx), facing)
        return when (move.value / 90) {
            1 -> r1
            else -> r1.rotate(Move(move.inst, move.value - 90), about, r)
        }
    }

    fun rotateClockwiseAbout(move: Move, about: Location) : Location = rotate(move, about, Clockwise)
    fun rotateAntiClockwiseAbout(move: Move, about: Location) : Location = rotate(move, about, AntiClockwise)
    
    fun advance(move : Move) : Location = when (move.inst) {
        North -> Location(x, y-move.value, facing)
        East -> Location(x+move.value, y, facing)
        South -> Location(x, y+move.value, facing)
        West -> Location(x-move.value, y, facing)
        Forward -> this.advance(Move(facing, move.value))
        Right -> turnClockwise(move.value)
        Left -> turnAntiClockwise(move.value)
    }
    
    fun advanceBy(l: Location, magnitude: Int) : Location = Location(
        x + (magnitude * l.x ), 
        y + (magnitude * l.y ), 
        facing
    ) 
    
    fun turnAntiClockwise(degrees : Int) : Location = turnThrough(degrees, compassDirections.reversed())
    fun turnClockwise(degrees : Int) : Location = turnThrough(degrees, compassDirections)
    
    private fun turnThrough(degrees : Int, directions : List<Inst>) : Location = Location(
        x, y, directions.cycle()
            .take(directions.indexOf(facing) + 1 + degrees / 90)
            .last()
    )

    operator fun minus(increment: Location): Location {
        return Location(x - increment.x, y- increment.y, facing)
    }
}

data class Ship(val location: Location, val waypoint: Location) {
    
    fun movePart1(move: Move) : Ship {
        return Ship(location.advance(move), waypoint)
    }

    fun movePart2(move: Move) : Ship {
        return if (move.inst == Forward) {
            val relativeWaypoint = waypoint - location
            val newLoc = location.advanceBy(relativeWaypoint, move.value)
            Ship(newLoc, newLoc.advanceBy(relativeWaypoint, 1))
        } else {
            Ship(location, waypoint.advanceWaypoint(move, location))
        }
    }
    
    fun manhattenDist() : Int = location.x.absoluteValue + location.y.absoluteValue

}

val initial = Ship(Location(0, 0, East), Location(10, -1, North))

fun steerPart01(moves : List<Move>) : Int = moves.fold(initial, { s, m -> s.movePart1(m)}).manhattenDist()
fun steerPart02(moves : List<Move>) : Int = moves.fold(initial, { s, m -> s.movePart2(m)}).manhattenDist()