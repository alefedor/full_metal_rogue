package ru.hse.spb.sd.full_metal_rogue.map

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
    private val actorGenerator: ActorGenerator = TrivialActorGenerator,
    private val mapInhabitator: MapInhabitator = SparseMapInhabitator,
    private val enemyCount: Int = STANDARD_ENEMY_COUNT
) : LevelGenerator {

    companion object {
        private const val STANDARD_ENEMY_COUNT = 3
    }

    override fun generateLevel(width: Int, height: Int): MutableGameMap {
        val map = mapGenerator.generateMap(width, height)

        val player = actorGenerator.generatePlayer()

        mapInhabitator.inhabitateWithActor(map, player)

        repeat(enemyCount) {
            val enemy = TrivialActorGenerator.generateEnemy()
            mapInhabitator.inhabitateWithActor(map, enemy)
        }

        return map
    }
}