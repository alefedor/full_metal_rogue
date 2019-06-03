package ru.hse.spb.sd.full_metal_rogue.logic.level

import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.AggressiveBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.CowardBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.PassiveBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.ContentGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Enemy
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import kotlin.random.Random

/**
 * Interface for generating different actors (players, enemies)
 */
interface ActorGenerator {
    fun generatePlayer(name: String): Player
    fun generateEnemy(): Enemy
}

class TrivialActorGenerator(val contentGenerator: ContentGenerator) : ActorGenerator {
    companion object {
        private val ENEMY_NAMES = listOf("oxygen", "water", "acid")
        private val BEHAVIOURS = listOf(PassiveBehaviour, AggressiveBehaviour, CowardBehaviour)
    }

    override fun generateEnemy(): Enemy {
        val behaviour = randomBehaviour()

        val prefix = when {
            behaviour is PassiveBehaviour -> "lazy "
            behaviour is AggressiveBehaviour -> "furious "
            behaviour is CowardBehaviour -> "coward "
            else -> ""
        }

        return Enemy(20, 3, behaviour, prefix + randomEnemyName(), 10, contentGenerator.generateChest())

    }
    override fun generatePlayer(name: String) = Player(100, 5, name)

    private fun randomEnemyName() = ENEMY_NAMES[Random.nextInt(ENEMY_NAMES.size)]

    private fun randomBehaviour() = BEHAVIOURS[Random.nextInt(BEHAVIOURS.size)]

}