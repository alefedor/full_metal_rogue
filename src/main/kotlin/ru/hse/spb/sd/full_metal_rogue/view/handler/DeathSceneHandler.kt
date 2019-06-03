package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.view.DeathView

/**
 * Handles user input on a DeathView.
 */
class DeathSceneHandler(private val player: Player) : GameSceneHandler() {
    private var backActionOccured = false
    override val view: DeathView?
        get() = if (backActionOccured) null else DeathView(player)

    /**
     * Returns a new StartView, which is to be output by the same SceneDrawer.
     */
    override fun backAction(): GameSceneHandler? {
        backActionOccured = true
        return this
    }
}