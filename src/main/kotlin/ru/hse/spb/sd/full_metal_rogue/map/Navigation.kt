package ru.hse.spb.sd.full_metal_rogue.map

import kotlin.random.Random

data class Position(var x: Int, var y: Int)

fun Position.applyDirection(direction: Direction) {
    when(direction) {
        Direction.Left -> x--
        Direction.Right -> x++
        Direction.Up -> y--
        Direction.Down -> y++
    }
}

/* Manhattan distance */
fun Position.distanceTo(other: Position) = Math.abs(x - other.x) + Math.abs(y - other.y)

enum class Direction {
    Left,
    Right,
    Up,
    Down;

    companion object {
        fun randomDirection(): Direction = Direction.values()[Random.nextInt(Direction.values().size)]
    }
}
