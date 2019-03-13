package ru.hse.spb.sd.full_metal_rogue.map

const val DEFAULT_MAP_HEIGHT = 36
const val DEFAULT_MAP_WIDTH = 85

interface LevelGenerator {
    fun generateLevel(width: Int = DEFAULT_MAP_WIDTH, height: Int = DEFAULT_MAP_HEIGHT): MutableGameMap
}

object CaveLevelGenerator : LevelGenerator {
    private val enemyCount = 3

    override fun generateLevel(width: Int, height: Int): MutableGameMap {
        val map = CaveMapGenerator.generateMap(width, height)

        val player = TrivialActorGenerator.generatePlayer()

        SparseMapInhabitator.inhabitateWithActor(map, player)

        repeat(enemyCount) {
            val enemy = TrivialActorGenerator.generateEnemy()
            SparseMapInhabitator.inhabitateWithActor(map, enemy)
        }

        return map
    }

}