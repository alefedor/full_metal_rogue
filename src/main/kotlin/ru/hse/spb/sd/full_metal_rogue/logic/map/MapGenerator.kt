package ru.hse.spb.sd.full_metal_rogue.logic.map

import ru.hse.spb.sd.full_metal_rogue.logic.objects.FreeSpace
import kotlin.math.roundToInt
import kotlin.random.Random

enum class FreeSpaceDensity(val density: Double) {
    Low(0.20),
    Medium(0.35),
    Strong(0.5)
}

/**
 * Interface for generating empty map consisting only of connected free spaces and walls
 */
interface MapGenerator {
    /**
     * Creates map with walls and free spaces. Objects on the bound are guaranteed to be walls.
     */
    fun generateMap(width: Int, height: Int, density: FreeSpaceDensity = FreeSpaceDensity.Medium): MutableGameMap
}

object CaveMapGenerator : MapGenerator {

    override fun generateMap(width: Int, height: Int, density: FreeSpaceDensity): MutableGameMap {
        val gameMap = MutableGameMap(width, height)

        if (width <= 2 || height <= 2)
            return gameMap

        val widthWithoutBounds = width - 2
        val heightWithoutBound = height - 2

        val needToFree = (widthWithoutBounds * heightWithoutBound * density.density).roundToInt()
        var freed = 0

        val position = Position(-1, -1)

        while (freed < needToFree) {
            val strictlyInBounds = 0 < position.x && position.x + 1 < width && 0 < position.y && position.y + 1 < height

            if (!strictlyInBounds) {
                do {
                    position.x = 1 + Random.nextInt(widthWithoutBounds)
                    position.y = 1 + Random.nextInt(heightWithoutBound)
                } while(freed != 0 && gameMap.get(position.x, position.y) != FreeSpace)
            }

            if (gameMap.get(position.x, position.y) != FreeSpace) {
                freed++
                gameMap.set(position.x, position.y, FreeSpace)
            }

            position.applyDirection(Direction.randomDirection())
        }

        return gameMap
    }
}