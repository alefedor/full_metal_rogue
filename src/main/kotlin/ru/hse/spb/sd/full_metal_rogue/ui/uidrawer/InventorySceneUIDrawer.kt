package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item
import ru.hse.spb.sd.full_metal_rogue.scene.Menu

class InventorySceneUIDrawer(terminal: AsciiPanel) : UIDrawer(terminal) {
    // the following values DO NOT take leftOffset into consideration
    // and can be used with any left offset
    private val inventoryItemTypePosition = 25
    private val inventoryBonusValuePosition = 32
    private val inventoryConfusionChancePosition = 40

    /**
     * Outputs inventory item menu and equipped items.
     */
    fun outputInventory(inventoryItems: Menu<Item>, equippedItems: List<Item>) {
        val inventoryOffset = leftOffset + (terminal.widthInCharacters - leftOffset) / 2
        outputHeader("Equipped items", leftOffset)
        outputHeader("Inventory", inventoryOffset)
        outputItemsHeaderForInventory()
        outputItemsHeaderForInventory(inventoryOffset)
        outputMenuItems(inventoryItems, inventoryOffset,
            inventoryOffset + inventoryItemTypePosition,
            inventoryOffset + inventoryBonusValuePosition,
            inventoryOffset + inventoryConfusionChancePosition)

        var y = 3
        for (item in equippedItems) {
            outputItem(item, y++, leftOffset,
                leftOffset + inventoryItemTypePosition,
                leftOffset + inventoryBonusValuePosition,
                leftOffset + inventoryConfusionChancePosition)
        }

    }

    private fun outputItemsHeaderForInventory(offset: Int = leftOffset) {
        terminal.write("Name", offset, 2, AsciiPanel.brightCyan)
        terminal.write("Type", offset + inventoryItemTypePosition, 2, AsciiPanel.brightCyan)
        terminal.write("Bonus", offset + inventoryBonusValuePosition, 2, AsciiPanel.brightCyan)
        terminal.write("Conf", offset + inventoryConfusionChancePosition, 2, AsciiPanel.brightCyan)
    }
}