package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.map.CaveLevelGenerator
import ru.hse.spb.sd.full_metal_rogue.map.MutableGameMap
import ru.hse.spb.sd.full_metal_rogue.scene.LevelScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent
import kotlin.system.exitProcess

class LevelSceneHandler(sceneDrawer: SceneDrawer, private val map: MutableGameMap) : SceneHandler(sceneDrawer) {
    private var message = ""
    override val scene: LevelScene
        get() = LevelScene(map, message)

    override fun handleUserInput(key: KeyEvent): SceneHandler? =
        when (key.keyCode) {
            KeyEvent.VK_ESCAPE -> null
            KeyEvent.VK_W -> makeGameTurn(PlayerMove.UP)
            //TODO reading map from file
            //VK_2 ->
            else -> this
        }

    private fun makeGameTurn(playerMove: PlayerMove): SceneHandler {
        TODO()
    }

    private enum class PlayerMove {
        UP, DOWN, LEFT, RIGHT
    }
}