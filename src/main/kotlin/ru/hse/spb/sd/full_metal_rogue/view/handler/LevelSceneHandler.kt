package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.*
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*
import ru.hse.spb.sd.full_metal_rogue.view.LevelView
import kotlin.random.Random

/**
 * Handles user input on a LevelView.
 */
class LevelSceneHandler(private val map: MutableGameMap) {
    private var backActionOccured = false
    private val messages = MessageNavigation()

    fun withPlayer(playerName: String) = LevelSceneHandlerWithPlayer(playerName)

    private fun move(from: Position, to: Position) {
        map[to] = map[from]
        map[from] = FreeSpace
    }

    fun moveEnemies() {
        val movedEnemies = HashSet<Actor>()
        for (x in 0 until map.width) {
            for (y in 0 until map.height) {
                val enemy = map[x, y]
                if (enemy is Enemy && !movedEnemies.contains(enemy)) {
                    moveEnemy(enemy, Position(x, y))
                    movedEnemies.add(enemy)
                }
            }
        }
    }

    /**
     * Moves specified enemy from specified position.
     */
    private fun moveEnemy(enemy: Enemy, position: Position) {
        val targetPosition = enemy.makeMove(position, map)
        if (targetPosition == position)
            return

        when(val targetTile = map[targetPosition]) {
            is FreeSpace -> {
                move(position, targetPosition)
            }

            is Actor -> {
                targetTile.takeDamage(enemy.attackPower)
                map[targetPosition] = targetTile.die() ?: FreeSpace
            }

            is Chest -> {
                val chest: Chest = targetTile
                enemy.absorbChest(chest)
                move(position, targetPosition)
            }
        }
    }

    inner class LevelSceneHandlerWithPlayer(private val playerName: String) : SceneHandler() {
        override val view: LevelView?
            get() = if (backActionOccured) null else LevelView(map, messages.getCurrentMessage(), playerName)
        /**
         * Saves the current map and exits current view.
         */
        override fun backAction(): SceneHandler? {
            backActionOccured = true
            return this
        }

        /**
         * Creates LevelSceneHandler for player's current inventory.
         */
        override fun selectAction(): SceneHandler? = InventorySceneHandler(map.player(playerName))

        /**
         * Makes game turn.
         */
        override fun directionAction(playerMove: Direction): SceneHandler {
            if (messages.hasNextMessage()) {
                messages.toNextMessage()
                return this
            }
            messages.clear()

            return movePlayer(playerName, playerMove)
        }

        /**
         * Moves player in specified direction.
         */
        private fun movePlayer(playerName: String, playerMove: Direction): SceneHandler {
            val currentPosition = map.playerPosition(playerName)
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

                is Actor -> {
                    val player = map.player(playerName)
                    targetTile.takeDamage(player.attackPower)

                    if (targetTile.isDead) {
                        val isLevelUp = player.earnExperience(targetTile.experienceCost)
                        messages.addMessage("You have slain the ${targetTile.name} " +
                                "and earned ${targetTile.experienceCost} experience points " +
                                "${if (isLevelUp) "(level up!)" else ""}.")
                        map[targetPosition] = targetTile.die() ?: FreeSpace
                    } else {
                        if (targetTile is Enemy && shouldConfuseEnemy(player)) {
                            targetTile.getConfused()
                            messages.addMessage("You hit and confused the ${targetTile.name}.")
                        } else {
                            messages.addMessage("You hit the ${targetTile.name}.")
                        }
                    }
                }

                is Chest -> {
                    move(currentPosition, targetPosition)
                    return ChestSceneHandler(targetTile, map.player(playerName))
                }
            }

            return this
        }

        private fun shouldConfuseEnemy(player: Player): Boolean = Random.nextDouble() < player.weapon.confusionChance
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