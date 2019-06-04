package ru.hse.spb.sd.full_metal_rogue.logic.map

import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Wall
import java.util.*

/**
 * Finds player in the GameMap and returns its position.
 */
fun GameMap.playerPosition(name: String): Position {
    for (x in 0 until width)
        for (y in 0 until height) {
            val gameObject = get(x, y)
            if (gameObject is Player && gameObject.name == name)
                return Position(x, y)
        }

    throw IllegalStateException("No player on the map, but player position queried")
}

/**
 * Gets positions of all players on the map.
 */
fun GameMap.playerPositions(): List<Position> {
    val playerPositions = mutableListOf<Position>()
    for (x in 0 until width)
        for (y in 0 until height) {
            val gameObject = get(x, y)
            if (gameObject is Player)
                playerPositions.add(Position(x, y))
        }
    return playerPositions
}

/**
 * Finds player in the GameMap and returns it.
 */
fun GameMap.player(name: String): Player = get(playerPosition(name)) as Player

/**
 * Checks whether a position in inside map constraints.
 */
fun GameMap.inBounds(position: Position) = position.x in 0 until width && 0 <= position.y && position.y < height

/**
 * Checks whether a position in the map can is traversable.
 */
fun GameMap.canPassThrough(position: Position) = inBounds(position) && get(position) !is Wall

/**
 * Calculates distances from the position to all other position considering only paths through traversable positions.
 */
fun GameMap.calculateDistancesFrom(positions: List<Position>): Array<IntArray> {
    positions.forEach { check(canPassThrough(it)) }

    val distances = Array(width) { IntArray(height) { Int.MAX_VALUE } }

    val queue = LinkedList<Position>()
    positions.forEach { queue.add(it) }
    positions.forEach { distances[it.x][it.y] = 0 }

    while (queue.isNotEmpty()) {
        val p = queue.poll()

        for (direction in Direction.values()) {
            val next = apply(p, direction)

            if (canPassThrough(next) && distances[next.x][next.y] == Int.MAX_VALUE) {
                distances[next.x][next.y] = distances[p.x][p.y] + 1
                queue.add(next)
            }
        }
    }

    return distances
}