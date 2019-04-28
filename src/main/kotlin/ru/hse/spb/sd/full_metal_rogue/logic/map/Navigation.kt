package ru.hse.spb.sd.full_metal_rogue.logic.map

import kotlin.random.Random

/**
 * Abstraction for position of an object in 2d-grid.
 */
data class Position(var x: Int, var y: Int)

/**
 * Moves a Position in direction denoted by a Direction.
 */
fun Position.applyDirection(direction: Direction) {
    when(direction) {
        Direction.LEFT -> x--
        Direction.RIGHT -> x++
        Direction.UP -> y--
        Direction.DOWN -> y++
    }
}

/**
 * Returns next Position in a given Direction from given Position.
 */
fun apply(position: Position, direction: Direction): Position {
    val newPosition = position.copy()
    newPosition.applyDirection(direction)
    return newPosition
}

/* Manhattan distance */
fun Position.distanceTo(other: Position) = Math.abs(x - other.x) + Math.abs(y - other.y)

/**
 * Abstraction for movements in 2d-grid.
 */
enum class Direction {
    LEFT,
    RIGHT,
    UP,
    DOWN;

    companion object {
        fun randomDirection(): Direction = Direction.values()[Random.nextInt(Direction.values().size)]
    }
}
