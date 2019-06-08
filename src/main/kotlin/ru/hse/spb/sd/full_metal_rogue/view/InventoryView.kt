package ru.hse.spb.sd.full_metal_rogue.view

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.InventorySceneUIDrawer

/**
 * Representation of player's inventory.
 */
class InventoryView(val inventoryItems: Menu<Item>,
                    val equippedItems: List<Item>
) : View {
    override fun draw(terminal: AsciiPanel) {
        val drawer = InventorySceneUIDrawer(terminal)
        drawer.clear()
        drawer.outputHeader("Inventory")
        drawer.outputInventory(inventoryItems, equippedItems)
    }
}