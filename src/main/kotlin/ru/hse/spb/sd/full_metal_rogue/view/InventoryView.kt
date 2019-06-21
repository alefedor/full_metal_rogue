package ru.hse.spb.sd.full_metal_rogue.view

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.UIDrawerFactory
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.ascii.InventorySceneAsciiUIDrawer

/**
 * Representation of player's inventory.
 */
class InventoryView(val inventoryItems: Menu<Item>,
                    val equippedItems: List<Item>
) : View {
    override fun draw(drawerFactory: UIDrawerFactory) {
        val drawer = drawerFactory.inventorySceneUIDrawer()
        drawer.clear()
        drawer.outputHeader("Inventory")
        drawer.outputInventory(inventoryItems, equippedItems)
    }
}