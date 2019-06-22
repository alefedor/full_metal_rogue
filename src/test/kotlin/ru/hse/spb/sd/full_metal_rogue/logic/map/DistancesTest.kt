package ru.hse.spb.sd.full_metal_rogue.logic.map

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.AggressiveBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.PassiveBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.SimpleContentGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Enemy
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player

class DistancesTest {
    @Test
    fun testDistancesToPlayers() {
        val map = MutableGameMap(5, 10)
        val lazyMob = Enemy(20, 1, PassiveBehaviour, "lazy mob", 10,
            SimpleContentGenerator.generateChest())
        val furiousMob = Enemy(20, 1, AggressiveBehaviour, "furious mob", 10,
            SimpleContentGenerator.generateChest())
        val player1 = Player(30, 2, "player1")
        val player2 = Player(30, 2, "player2")
        map[3, 7] = lazyMob
        map[2, 3] = furiousMob
        map[2, 4] = player1
        map[4, 9] = player2
        val distances = map.calculateDistancesFrom(listOf(Position(2, 4), Position(4, 9)))
        for (i in 0 until distances.size) {
            for (j in 0 until distances[i].size) {
                when {
                    i == 2 && j == 4 || i == 4 && j == 9 -> assertEquals(0 ,distances[i][j])
                    i == 2 && j == 3 -> assertEquals(1 ,distances[i][j])
                    else -> assertEquals(Int.MAX_VALUE, distances[i][j])
                }
            }
        }
    }
}