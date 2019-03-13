package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.map.FileMapLoader
import ru.hse.spb.sd.full_metal_rogue.map.MutableGameMap
import ru.hse.spb.sd.full_metal_rogue.objects.*
import ru.hse.spb.sd.full_metal_rogue.scene.LevelScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent

class LevelSceneHandler(sceneDrawer: SceneDrawer, private val map: MutableGameMap) : SceneHandler(sceneDrawer) {
    private var message = ""
    override val scene: LevelScene
        get() = LevelScene(map, message)

    override fun handleUserInput(key: KeyEvent): SceneHandler? =
        when (key.keyCode) {
            KeyEvent.VK_ESCAPE -> null
            KeyEvent.VK_W -> makeGameTurn(PlayerMove.UP)
            KeyEvent.VK_S -> makeGameTurn(PlayerMove.DOWN)
            KeyEvent.VK_A -> makeGameTurn(PlayerMove.LEFT)
            KeyEvent.VK_D -> makeGameTurn(PlayerMove.RIGHT)
            KeyEvent.VK_P -> this.also { FileMapLoader.saveMap(map) }
            else -> this
        }

    private fun movePlayer(player: Player, playerMove: PlayerMove, oldX: Int, oldY: Int): Boolean {
        val newX = oldX + playerMove.deltaX
        val newY = oldY + playerMove.deltaY
        if (isInMapBounds(newX, newY)) {
            when (map[newX, newY]) {
                is FreeSpace -> {
                    map[oldX, oldY] = FreeSpace
                    map[newX, newY] = player
                    this.message = ""
                    return true
                }
                is Wall -> this.message = "There is a wall in the way!"
                is Enemy -> {
                    // TODO handle mob confrontation
                }
            }
        } else {
            this.message = "There is a border in the way!"
        }

        return false
    }

    private fun makeGameTurn(playerMove: PlayerMove): SceneHandler {
        val movedActors = HashSet<Actor>()

        for (i in 0 until map.height) {
            for (j in 0 until map.width) {
                if (map[j, i] is Actor && !movedActors.contains(map[j, i])) {
                    when (map[j, i]) {
                        is Player -> {
                            val player: Player = map[j, i] as Player
                            if (movePlayer(player, playerMove, j, i)) {
                                movedActors.add(player)
                            }
                        }
                        is Enemy -> {
                            // TODO handle mob movement
                            //movedActors.add(map[j, i] as Actor)
                        }
                    }
                }
            }
        }

        return this
    }

    private fun isInMapBounds(x: Int, y: Int): Boolean =
        x >= 0 && x < map.width && y >= 0 && y < map.height

    private enum class PlayerMove(val deltaX: Int, val deltaY: Int) {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0)
    }
}