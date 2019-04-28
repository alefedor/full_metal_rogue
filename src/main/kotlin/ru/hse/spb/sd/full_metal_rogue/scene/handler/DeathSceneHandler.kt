package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.scene.DeathScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent

/**
 * Handles user input on a DeathScene
 */
class DeathSceneHandler(private val sceneDrawer: SceneDrawer,
                        private val player: Player
) : SceneHandler(sceneDrawer) {
    override val scene: DeathScene
        get() = DeathScene(player)

    /**
     * @see [SceneHandler.handleUserInput]
     */
    override fun handleUserInput(key: KeyEvent): SceneHandler? =
        when (key.keyCode) {
            KeyEvent.VK_ESCAPE -> StartSceneHandler(sceneDrawer)
            else -> this
        }
}