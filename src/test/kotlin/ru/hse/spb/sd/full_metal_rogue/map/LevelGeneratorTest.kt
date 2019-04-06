package ru.hse.spb.sd.full_metal_rogue.map

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.hse.spb.sd.full_metal_rogue.objects.Player

@RunWith(Parameterized::class)
class LevelGeneratorTest(val levelGenerator: LevelGenerator) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun levelGeneratorImplementations() = listOf<LevelGenerator>(StandardLevelGenerator())
    }

    @Test
    fun testGenerateLevel() {
        val level = levelGenerator.generateLevel()
        assertEquals(LevelGenerator.DEFAULT_MAP_WIDTH, level.width)
        assertEquals(LevelGenerator.DEFAULT_MAP_HEIGHT, level.height)
    }

    @Test
    fun testGenerateLevelSinglePlayer() {
        val level = levelGenerator.generateLevel()
        var numberOfPlayers = 0

        for (x in 0 until level.width)
            for (y in 0 until level.height)
                if (level[x, y] is Player)
                    numberOfPlayers++

        assertEquals(1, numberOfPlayers)
    }
}