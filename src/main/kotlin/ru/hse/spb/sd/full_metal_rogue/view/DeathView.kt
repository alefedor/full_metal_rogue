package ru.hse.spb.sd.full_metal_rogue.view

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.UIDrawerFactory
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.ascii.DeathSceneAsciiUIDrawer

/**
 * Representation of death screen.
 */
class DeathView(val player: Player) : View {
    override fun draw(drawerFactory: UIDrawerFactory) {
        val drawer = drawerFactory.deathSceneUIDrawer()
        drawer.clear()
        drawer.outputDeathMessage(player)
    }
}