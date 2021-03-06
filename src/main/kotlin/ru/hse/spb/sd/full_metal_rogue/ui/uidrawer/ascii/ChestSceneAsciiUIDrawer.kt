package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.ascii

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.ChestSceneUIDrawer
import ru.hse.spb.sd.full_metal_rogue.view.Menu

/**
 * Handles writing to terminal for ChestView.
 */
class ChestSceneAsciiUIDrawer(terminal: AsciiPanel) : AsciiUIDrawer(terminal), ChestSceneUIDrawer {
    // the following values DO take leftOffset into consideration
    private val chestBonusValuePosition = 65
    private val chestConfusionChancePosition = 80
    private val chestItemTypePosition = 56

    /**
     * Outputs a chest item menu.
     */
    override fun outputChest(chestItemMenu: Menu<Item>) {
        outputItemsHeaderForChest()
        outputMenuItems(chestItemMenu, leftOffset,
            chestItemTypePosition, chestBonusValuePosition, chestConfusionChancePosition)
    }

    private fun outputItemsHeaderForChest() {
        terminal.write("Name", leftOffset, 2, AsciiPanel.brightCyan)
        terminal.write("Type", chestItemTypePosition, 2, AsciiPanel.brightCyan)
        terminal.write("Bonus", chestBonusValuePosition, 2, AsciiPanel.brightCyan)
        terminal.write("Confusion", chestConfusionChancePosition, 2, AsciiPanel.brightCyan)
    }
}