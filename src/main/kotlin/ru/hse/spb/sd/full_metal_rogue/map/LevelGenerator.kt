package ru.hse.spb.sd.full_metal_rogue.map

import ru.hse.spb.sd.full_metal_rogue.objects.Player

interface LevelGenerator {
    fun generateLevel(width: Int, height: Int): MutableGameMap
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