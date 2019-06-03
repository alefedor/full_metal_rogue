package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.view.DeathView
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer

/**
 * Handles user input on a DeathView.
 */
class DeathSceneHandler(private val sceneDrawer: SceneDrawer,
                        private val player: Player
) : SceneHandler(sceneDrawer) {
    override val view: DeathView
        get() = DeathView(player)

    /**
     * Returns a new StartView, which is to be output by the same SceneDrawer.
     */
    override fun backAction(): SceneHandler? = StartSceneHandler(sceneDrawer)
}