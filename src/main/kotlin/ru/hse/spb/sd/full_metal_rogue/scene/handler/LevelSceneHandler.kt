package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.*
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*
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
            KeyEvent.VK_W -> makeGameTurn(Direction.UP)
            KeyEvent.VK_S -> makeGameTurn(Direction.DOWN)
            KeyEvent.VK_A -> makeGameTurn(Direction.LEFT)
            KeyEvent.VK_D -> makeGameTurn(Direction.RIGHT)
            KeyEvent.VK_P -> this.also { FileMapLoader.saveMap(map) }
            else -> this
        }

    private fun movePlayer(player: Player, playerMove: Direction, oldPosition: Position): Boolean {
        val newPosition = oldPosition.goToDirection(playerMove)

        if (map.inBounds(newPosition)) {
            when (map[newPosition]) {
                is FreeSpace -> {
                    map[oldPosition] = FreeSpace
                    map[newPosition] = player
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

    private fun makeGameTurn(playerMove: Direction): SceneHandler {
        val movedActors = HashSet<Actor>()

        for (x in 0 until map.width) {
            for (y in 0 until map.height) {
                if (map[x, y] is Actor && !movedActors.contains(map[x, y])) {
                    when (map[x, y]) {
                        is Player -> {
                            val player: Player = map[x, y] as Player
                            if (movePlayer(player, playerMove, Position(x, y))) {
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
}