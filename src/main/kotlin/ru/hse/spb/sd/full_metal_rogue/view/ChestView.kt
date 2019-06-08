package ru.hse.spb.sd.full_metal_rogue.view

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.ChestSceneUIDrawer

/**
 * Representation of chest content.
 */
class ChestView(val chestItems: Menu<Item>) : View {
    override fun draw(terminal: AsciiPanel) {
        val drawer = ChestSceneUIDrawer(terminal)
        drawer.clear()
        drawer.outputHeader("Chest")
        drawer.outputChest(chestItems)
    }
}