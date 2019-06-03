package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.*
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*
import ru.hse.spb.sd.full_metal_rogue.view.LevelView
import kotlin.random.Random

/**
 * Handles user input on a LevelView.
 */
class LevelSceneHandler(private val map: MutableGameMap) : SceneHandler() {
    private val messages = MessageNavigation()
    override val view: LevelView
        get() = LevelView(map, messages.getCurrentMessage())

    /**
     * Saves the current map and exits current view.
     */
    override fun backAction(): SceneHandler? {
        FileMapLoader.saveMap(map)
        return null
    }

    /**
     * Creates LevelSceneHandler for player's current inventory.
     */
    override fun selectAction(): SceneHandler? = InventorySceneHandler(map.player())

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
                    val scene = moveEnemy(enemy, Position(x, y))
                    if (scene is DeathSceneHandler) {
                        nextScene = scene
                    }
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
        val targetPosition = apply(currentPosition, playerMove)
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
                move(currentPosition, targetPosition)
                return ChestSceneHandler(targetTile, map.player())
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
                    FileMapLoader.deleteMap()
                    return DeathSceneHandler(targetTile)
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

    /**
     * If there is next message, changes current message to it.
     */
    fun toNextMessage() {
        if (hasNextMessage()) {
            currentMessageIndex++
        }
    }

    /**
     * Checks if there is a next message.
     */
    fun hasNextMessage() = currentMessageIndex < messages.lastIndex

    /**
     * Returns current message.
     */
    fun getCurrentMessage() =
        if (messages.isEmpty()) "" else "${messages[currentMessageIndex]} (${currentMessageIndex + 1}/${messages.size})"

    /**
     * Adds new message to end.
     */
    fun addMessage(message: String) = messages.add(message)

    /**
     * Removes messages and resets current message.
     */
    fun clear() {
        messages.clear()
        currentMessageIndex = 0
    }
}