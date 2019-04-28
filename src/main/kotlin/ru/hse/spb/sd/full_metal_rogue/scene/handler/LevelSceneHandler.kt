package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.Game
import ru.hse.spb.sd.full_metal_rogue.logic.map.*
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*
import ru.hse.spb.sd.full_metal_rogue.scene.LevelScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent
import java.nio.file.Files
import java.nio.file.Paths
import java.lang.Integer.max
import kotlin.random.Random

/**
 * Handles user input on a LevelScene.
 */
class LevelSceneHandler(private val sceneDrawer: SceneDrawer,
                        private val map: MutableGameMap
) : SceneHandler(sceneDrawer) {
    private val messages = MessageNavigation()
    override val scene: LevelScene
        get() = LevelScene(map, messages.getCurrentMessage())

    /**
     * @see SceneHandler.backAction
     */
    override fun backAction(): SceneHandler? = null

    /**
     * Returns InventorySceneHandler to be replaced by it.
     */
    override fun selectAction(): SceneHandler? = InventorySceneHandler(sceneDrawer, map.player().inventory)

    /**
     * Makes game turn.
     */
    override fun directionAction(playerMove: Direction): SceneHandler {
        if (messages.hasNextMessage()) {
            messages.toNextMessage()
            return this
        }
        messages.clear()

        var nextScene = movePlayer(playerMove)
        val movedEnemies = HashSet<Actor>()
        for (x in 0 until map.width) {
            for (y in 0 until map.height) {
                val enemy = map[x, y]
                if (enemy is Enemy && !movedEnemies.contains(enemy)) {
                    nextScene = moveEnemy(enemy, Position(x, y))
                    movedEnemies.add(enemy)
                }
            }
        }

        return nextScene
    }

    private fun move(from: Position, to: Position) {
        map[to] = map[from]
        map[from] = FreeSpace
    }

    /**
     * Moves player in specified direction.
     */
    private fun movePlayer(playerMove: Direction): SceneHandler {
        val currentPosition = map.playerPosition()
        val targetPosition = currentPosition.goToDirection(playerMove)
        val targetTile = map[targetPosition]

        if (!map.inBounds(targetPosition) || targetTile is Wall) {
            messages.addMessage("There is a wall in the way!")
            return this
        }

        when (targetTile) {
            is FreeSpace -> {
                move(currentPosition, targetPosition)
            }

            is Enemy -> {
                val player = map.player()
                targetTile.takeDamage(player.attackPower)

                if (targetTile.isDead) {
                    val isLevelUp = player.earnExperience(targetTile.experienceCost)
                    messages.addMessage("You have slain the ${targetTile.name} " +
                            "and earned ${targetTile.experienceCost} experience points " +
                            "${if (isLevelUp) "(level up!)" else ""}.")
                    map[targetPosition] = targetTile.die() ?: FreeSpace
                } else {
                    if (shouldConfuseEnemy(player)) {
                        targetTile.getConfused()
                        messages.addMessage("You confused the ${targetTile.name}.")
                    }
                    messages.addMessage("You hit the ${targetTile.name}.")
                }
            }

            is Chest -> {
                // TODO: move player and open chest Inventory screen
            }
        }

        return this
    }

    private fun shouldConfuseEnemy(player: Player): Boolean = Random.nextDouble() < player.weapon.confusionChance

    /**
     * Moves specified enemy from specified position.
     */
    private fun moveEnemy(enemy: Enemy, position: Position): SceneHandler {
        val targetPosition = enemy.makeMove(position, map)
        if (targetPosition == position) {
            return this
        }

        when(val targetTile = map[targetPosition]) {
            is FreeSpace -> {
                move(position, targetPosition)
            }

            is Actor -> {
                targetTile.takeDamage(enemy.attackPower)
                if (targetTile.isDead && targetTile is Player) {
                    Files.deleteIfExists(Paths.get(Game.SAVE_NAME))
                    return DeathSceneHandler(sceneDrawer, targetTile)
                } else if (targetTile.isDead && targetTile is Enemy) {
                    map[targetPosition] = targetTile.die() ?: FreeSpace
                }
            }

            is Chest -> {
                val chest: Chest = targetTile
                enemy.absorbChest(chest)
                move(position, targetPosition)
            }
        }

        return this
    }
}

/**
 * Handles navigation through multiple game messages.
 */
private class MessageNavigation {
    private val messages = mutableListOf<String>()
    private var currentMessageIndex = 0

    fun toNextMessage() {
        if (hasNextMessage()) {
            currentMessageIndex++
        }
    }

    fun hasNextMessage() = currentMessageIndex < messages.lastIndex

    fun getCurrentMessage() =
        if (messages.isEmpty()) "" else "${messages[currentMessageIndex]} (${currentMessageIndex + 1}/${messages.size})"

    fun addMessage(message: String) = messages.add(message)

    fun clear() {
        messages.clear()
        currentMessageIndex = 0
    }
}