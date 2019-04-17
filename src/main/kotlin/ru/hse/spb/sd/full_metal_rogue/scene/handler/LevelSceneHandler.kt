package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.*
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*
import ru.hse.spb.sd.full_metal_rogue.scene.LevelScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent
import kotlin.random.Random

/**
 * Class that handles user input on a LevelScene
 */
class LevelSceneHandler(private val sceneDrawer: SceneDrawer,
                        private val map: MutableGameMap
) : SceneHandler(sceneDrawer) {
    private val messages = mutableListOf<String>()
    private var currentMessageIndex = 0
    override val scene: LevelScene
        get() = LevelScene(map, getCurrentMessage())

    /**
     * @see [SceneHandler.handleUserInput]
     */
    override fun handleUserInput(key: KeyEvent): SceneHandler? =
        when (key.keyCode) {
            KeyEvent.VK_ESCAPE -> null
            KeyEvent.VK_W -> makeGameTurn(Direction.UP)
            KeyEvent.VK_S -> makeGameTurn(Direction.DOWN)
            KeyEvent.VK_A -> makeGameTurn(Direction.LEFT)
            KeyEvent.VK_D -> makeGameTurn(Direction.RIGHT)
            KeyEvent.VK_P -> this.also { FileMapLoader.saveMap(map) }
            KeyEvent.VK_RIGHT -> displayNextMessage()
            KeyEvent.VK_LEFT -> displayPreviousMessage()
            else -> this
        }

    /**
     * Makes enemies and player turns
     */
    private fun makeGameTurn(playerMove: Direction): SceneHandler {
        clearMessages()
        movePlayer(playerMove)

        val movedEnemies = HashSet<Actor>()
        for (x in 0 until map.width) {
            for (y in 0 until map.height) {
                val enemy = map[x, y]
                if (enemy is Enemy && !movedEnemies.contains(enemy)) {
                    if (moveEnemy(enemy, Position(x, y))) {
                        return DeathSceneHandler(sceneDrawer, map.player())
                    }
                    movedEnemies.add(enemy)
                }
            }
        }

        return this
    }

    /**
     * Moves player in specified direction
     */
    private fun movePlayer(playerMove: Direction) {
        val currentPosition = map.playerPosition()
        val targetPosition = currentPosition.goToDirection(playerMove)
        val targetTile = map[targetPosition]

        if (!map.inBounds(targetPosition) || targetTile is Wall) {
            messages.add("There is a wall in the way!")
            return
        }

        when (targetTile) {
            is FreeSpace -> {
                map[targetPosition] = map[currentPosition]
                map[currentPosition] = FreeSpace
            }

            is Enemy -> {
                val player = map.player()
                targetTile.takeDamage(player.attackPower)

                if (targetTile.isDead) {
                    messages.add("You have slain the ${targetTile.name}.")
                    map[targetPosition] = FreeSpace
                } else {
                    if ((shouldConfuseEnemy())) {
                        targetTile.getConfused()
                        messages.add("You confused the ${targetTile.name}.")
                    }
                    messages.add("You hit the ${targetTile.name}.")
                }
            }
        }
    }

    /**
     * Moves specified enemy from specified position
     *
     * @return true if the enemy has slain the player, and false otherwise
     */
    private fun moveEnemy(enemy: Enemy, position: Position): Boolean {
        val targetPosition = enemy.makeMove(position, map)
        val targetTile = map[targetPosition]

        when(targetTile) {
            is FreeSpace -> {
                map[targetPosition] = enemy
                map[position] = FreeSpace
            }

            is Player -> {
                targetTile.takeDamage(enemy.attackPower)
                if (targetTile.isDead) {
                    return true
                }
            }
        }

        return false
    }

    private fun displayNextMessage(): SceneHandler {
        if (currentMessageIndex + 1 < messages.size) {
            currentMessageIndex++
        }
        return this
    }

    private fun displayPreviousMessage(): SceneHandler {
        if (currentMessageIndex - 1 >= 0) {
            currentMessageIndex--
        }
        return this
    }

    private fun getCurrentMessage(): String {
        return if (messages.isEmpty()) {
            ""
        } else {
            "${messages[currentMessageIndex]} (${currentMessageIndex + 1}/${messages.size})"
        }
    }

    private fun clearMessages() {
        messages.clear()
        currentMessageIndex = 0
    }

    private fun shouldConfuseEnemy(): Boolean = Random.nextBoolean()
}