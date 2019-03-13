package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.map.MutableGameMap
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
            //TODO reading map from file
            //VK_2 ->
            else -> this
        }

    private fun makeGameTurn(playerMove: PlayerMove): SceneHandler {
        TODO()
    }

    private enum class PlayerMove(val deltaX: Int, val deltaY: Int) {
        UP(0, 1),
        DOWN(0, -1),
        LEFT(-1, 0),
        RIGHT(1, 0)
    }
}