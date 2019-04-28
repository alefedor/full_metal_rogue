package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.scene.DeathScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer

/**
 * Handles user input on a DeathScene
 */
class DeathSceneHandler(private val sceneDrawer: SceneDrawer,
                        private val player: Player
) : SceneHandler(sceneDrawer) {
    override val scene: DeathScene
        get() = DeathScene(player)

    /**
     * Returns InventorySceneHandler to be replaced by it.
     */
    override fun backAction(): SceneHandler? = StartSceneHandler(sceneDrawer)
}