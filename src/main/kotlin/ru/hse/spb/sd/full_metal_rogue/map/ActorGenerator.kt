package ru.hse.spb.sd.full_metal_rogue.map

import ru.hse.spb.sd.full_metal_rogue.objects.Enemy
import ru.hse.spb.sd.full_metal_rogue.objects.Player
import kotlin.random.Random

interface ActorGenerator {
    fun generatePlayer(): Player
    fun generateEnemy(): Enemy
}

object TrivialActorGenerator : ActorGenerator {
    private val enemyNames = listOf("oxygen", "water", "acid")

    override fun generateEnemy() = Enemy(20, 3, 5, randomEnemyName())

    override fun generatePlayer() = Player(100, 5)

    private fun randomEnemyName() = enemyNames[Random.nextInt(enemyNames.size)]
}