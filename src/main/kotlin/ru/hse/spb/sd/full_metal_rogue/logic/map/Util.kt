package ru.hse.spb.sd.full_metal_rogue.logic.map

import ru.hse.spb.sd.full_metal_rogue.logic.objects.Enemy
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Wall
import java.util.*

fun GameMap.playerPosition(): Position {
    for (x in 0 until width)
        for (y in 0 until height)
            if (get(x, y) is Player)
                return Position(x, y)

    throw IllegalStateException("No player on the map, but player position queried")
}

fun GameMap.player(): Player = get(playerPosition()) as Player

fun GameMap.inBounds(position: Position) = position.x in 0..(width - 1) && 0 <= position.y && position.y < height

fun GameMap.canPassThrough(position: Position) = inBounds(position) && get(position) !is Wall

fun GameMap.calculateDistancesFrom(position: Position): Array<IntArray> {
    check(canPassThrough(position))

    val distances = Array(width) { IntArray(height) { Int.MAX_VALUE } }

    val queue = LinkedList<Position>()
    queue.add(position)
    distances[position.x][position.y] = 0

    while (queue.isNotEmpty()) {
        val p = queue.poll()

        for (direction in Direction.values()) {
            val next = p.goToDirection(direction)

            if (canPassThrough(next) && distances[next.x][next.y] == Int.MAX_VALUE) {
                distances[next.x][next.y] = distances[p.x][p.y] + 1
                queue.add(next)
            }
        }
    }

    return distances
}