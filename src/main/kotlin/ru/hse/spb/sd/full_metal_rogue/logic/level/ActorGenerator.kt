package ru.hse.spb.sd.full_metal_rogue.logic.level

import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.AgressiveBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.CowardBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.PassiveBehaviour
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Enemy
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import kotlin.random.Random

/**
 * Interface for generating different actors (players, enemies)
 */
interface ActorGenerator {
    fun generatePlayer(): Player
    fun generateEnemy(): Enemy
}

object TrivialActorGenerator : ActorGenerator {
    private val enemyNames = listOf("oxygen", "water", "acid")
    private val behaviours = listOf(PassiveBehaviour, AgressiveBehaviour, CowardBehaviour)

    override fun generateEnemy() = Enemy(20, 3, randomBehaviour(), randomEnemyName())

    override fun generatePlayer() = Player(100, 5)

    private fun randomEnemyName() = enemyNames[Random.nextInt(enemyNames.size)]

    private fun randomBehaviour() = behaviours[Random.nextInt(behaviours.size)]

}