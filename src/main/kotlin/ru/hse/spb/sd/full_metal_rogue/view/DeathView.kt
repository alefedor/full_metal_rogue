package ru.hse.spb.sd.full_metal_rogue.view

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.DeathSceneUIDrawer

/**
 * Representation of death screen.
 */
class DeathView(val player: Player) : View {
    override fun draw(terminal: AsciiPanel) {
        val drawer = DeathSceneUIDrawer(terminal)
        drawer.clear()
        drawer.outputDeathMessage(player)
    }
}