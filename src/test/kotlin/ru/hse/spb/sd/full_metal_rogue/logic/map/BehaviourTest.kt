package ru.hse.spb.sd.full_metal_rogue.logic.map

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.AggressiveBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.ConfusionDecorator
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.CowardBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.PassiveBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.objects.FreeSpace
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player

class BehaviourTest {
    @Test
    fun testLazyBehaviour() {
        val map = MutableGameMap(5, 10)
        for (i in 2..4) {
            for (j in 6..8) {
                map[i, j] = FreeSpace
            }
        }

        for (i in 0..2) {
            val position = PassiveBehaviour.makeMove(Position(3, 7), map)
            assertEquals(3, position.x)
            assertEquals(7, position.y)
        }
    }

    @Test
    fun testCowardBehaviour() {
        val map = MutableGameMap(5, 10)
        for (i in 2..4) {
            for (j in 6..8) {
                map[i, j] = FreeSpace
            }
        }

        val player = Player(30, 2, "player")
        map[3, 6] = player
        val initialEnemyPosition = Position(3, 7)
        val initialPlayerPosition = Position(3, 6)
        val newEnemyPosition = CowardBehaviour.makeMove(initialEnemyPosition, map)
        assertTrue(initialEnemyPosition.distanceTo(initialPlayerPosition) <=
                newEnemyPosition.distanceTo(initialPlayerPosition))
    }

    @Test
    fun testAggressiveBehaviour() {
        val map = MutableGameMap(5, 10)
        for (i in 2..4) {
            for (j in 6..8) {
                map[i, j] = FreeSpace
            }
        }

        val player = Player(30, 2, "player")
        map[3, 6] = player
        val initialEnemyPosition = Position(3, 8)
        val initialPlayerPosition = Position(3, 6)
        val newEnemyPosition = AggressiveBehaviour.makeMove(initialEnemyPosition, map)
        assertTrue(initialEnemyPosition.distanceTo(initialPlayerPosition) >=
                newEnemyPosition.distanceTo(initialPlayerPosition))
    }

    @Test
    fun testConfusedBehaviour() {
        val map = MutableGameMap(5, 10)
        for (i in 1..4) {
            for (j in 5..9) {
                map[i, j] = FreeSpace
            }
        }

        val behaviour = ConfusionDecorator(PassiveBehaviour)
        val confusedPositions = mutableListOf(Position(3, 7))
        for (i in 0..4) {
            confusedPositions.add(behaviour.makeMove(confusedPositions.last(), map))
        }
        // there exists at least one such position that the mob moved to at random
        assertTrue(confusedPositions.find { it.x != 3 || it.y != 7 } != null)

    }
}