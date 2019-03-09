package ru.hse.spb.sd.full_metal_rogue.map

import kotlin.random.Random

data class Position(var x: Int, var y: Int) {
    fun applyDirection(direction: Direction) {
        when(direction) {
            Direction.Left -> x--
            Direction.Right -> x++
            Direction.Up -> y--
            Direction.Down -> y++
        }
    }
}

enum class Direction {
    Left,
    Right,
    Up,
    Down;

    companion object {
        fun randomDirection(): Direction = Direction.values()[Random.nextInt(Direction.values().size)]
    }
}
