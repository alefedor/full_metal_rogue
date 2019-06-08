package ru.hse.spb.sd.full_metal_rogue.logic.level

import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.AggressiveBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.CowardBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.PassiveBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.ContentGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Enemy
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import java.awt.Color
import kotlin.random.Random

/**
 * Interface for generating different actors (players, enemies)
 */
interface ActorGenerator {
    /**
     * Generates a player.
     */
    fun generatePlayer(name: String): Player

    /**
     * Generates an enemy.
     */
    fun generateEnemy(): Enemy
}

class TrivialActorGenerator(private val contentGenerator: ContentGenerator) : ActorGenerator {
    companion object {
        private val ENEMY_NAMES = listOf("oxygen", "water", "acid")
        private val BEHAVIOURS = listOf(PassiveBehaviour, AggressiveBehaviour, CowardBehaviour)
    }

    override fun generateEnemy(): Enemy {
        val behaviour = randomBehaviour()

        val prefix = when (behaviour) {
            is PassiveBehaviour -> "lazy "
            is AggressiveBehaviour -> "furious "
            is CowardBehaviour -> "coward "
            else -> ""
        }

        val color = when (behaviour) {
            is PassiveBehaviour -> Color.YELLOW
            is AggressiveBehaviour -> Color.GREEN
            is CowardBehaviour -> Color.CYAN
            else -> Color.RED
        }

        return Enemy(20, 3, behaviour, prefix + randomEnemyName(), 10, contentGenerator.generateChest(), color)

    }
    override fun generatePlayer(name: String) = Player(100, 5, name)

    private fun randomEnemyName() = ENEMY_NAMES[Random.nextInt(ENEMY_NAMES.size)]

    private fun randomBehaviour() = BEHAVIOURS[Random.nextInt(BEHAVIOURS.size)]

}