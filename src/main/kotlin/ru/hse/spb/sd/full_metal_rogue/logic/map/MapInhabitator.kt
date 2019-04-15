package ru.hse.spb.sd.full_metal_rogue.logic.map

import ru.hse.spb.sd.full_metal_rogue.logic.objects.Actor
import ru.hse.spb.sd.full_metal_rogue.logic.objects.FreeSpace
import kotlin.random.Random

/**
 * Interface for placing actors into map.
 */
interface MapInhabitator {
    fun inhabitateWithActor(map: MutableGameMap, actor: Actor)
}

object SparseMapInhabitator : MapInhabitator {
    override fun inhabitateWithActor(map: MutableGameMap, actor: Actor) {
        val actorPositions = mutableListOf<Position>()
        val freeSpacePositions = mutableListOf<Position>()

        for (x in 0 until map.width)
            for (y in 0 until map.height) {
                if (map[x, y] == FreeSpace)
                    freeSpacePositions.add(Position(x, y))
                if (map[x, y] is Actor)
                    actorPositions.add(Position(x, y))
            }

        val position = randomPosition(map, freeSpacePositions, actorPositions)

        map[position.x, position.y] = actor
    }

    private fun randomPosition(map: MutableGameMap, freeSpacePositions: List<Position>, actorPositions: List<Position>): Position {
        if (freeSpacePositions.isEmpty())
            throw IllegalArgumentException("Map should have at least one free space")

        val weights = DoubleArray(freeSpacePositions.size) { (map.width + map.height).toDouble() }

        for (i in 0 until weights.size)
            for (actorPosition in actorPositions)
                weights[i] = Math.min(weights[i], freeSpacePositions[i].distanceTo(actorPosition).toDouble())

        val totalWeight = weights.sum()

        val rand = Random.nextDouble(totalWeight)

        var sumWeight = 0.0

        for (i in 0 until freeSpacePositions.size) {
            sumWeight += weights[i]

            if (sumWeight >= rand) {
                return freeSpacePositions[i]
            }
        }

        return freeSpacePositions.last()
    }

}