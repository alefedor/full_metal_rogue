package ru.hse.spb.sd.full_metal_rogue.logic.map

import kotlin.random.Random

data class Position(var x: Int, var y: Int)

fun Position.applyDirection(direction: Direction) {
    when(direction) {
        Direction.LEFT -> x--
        Direction.RIGHT -> x++
        Direction.UP -> y--
        Direction.DOWN -> y++
    }
}

fun Position.goToDirection(direction: Direction): Position {
    val position = copy()
    position.applyDirection(direction)
    return position
}

/* Manhattan distance */
fun Position.distanceTo(other: Position) = Math.abs(x - other.x) + Math.abs(y - other.y)

enum class Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    companion object {
        fun randomDirection(): Direction = Direction.values()[Random.nextInt(Direction.values().size)]
    }
}
