package ru.hse.spb.sd.full_metal_rogue.logic.level

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.SimpleContentGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.*

/**
 * Interface for generation complete level with player and enemies
 */
interface LevelGenerator {
    companion object {
        const val DEFAULT_MAP_HEIGHT = 38
        const val DEFAULT_MAP_WIDTH = 85
    }

    fun generateLevel(width: Int = DEFAULT_MAP_WIDTH, height: Int = DEFAULT_MAP_HEIGHT): MutableGameMap
}

class StandardLevelGenerator(
    private val mapGenerator: MapGenerator = CaveMapGenerator,
    private val actorGenerator: ActorGenerator = TrivialActorGenerator(SimpleContentGenerator),
    private val mapInhabitator: MapInhabitator = SparseMapInhabitator,
    private val enemyCount: Int = STANDARD_ENEMY_COUNT
) : LevelGenerator {

    companion object {
        private const val STANDARD_ENEMY_COUNT = 3
    }

    override fun generateLevel(width: Int, height: Int): MutableGameMap {
        val map = mapGenerator.generateMap(width, height)

        repeat(enemyCount) {
            val enemy = actorGenerator.generateEnemy()
            mapInhabitator.inhabitateWithActor(map, enemy)
        }

        return map
    }
}