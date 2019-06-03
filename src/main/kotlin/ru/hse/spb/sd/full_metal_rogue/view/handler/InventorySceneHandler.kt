package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.view.InventoryView
import ru.hse.spb.sd.full_metal_rogue.view.MutableMenu

/**
 * Handles user input on a InventoryView.
 */
class InventorySceneHandler(private val player: Player) : SceneHandler() {
    private val inventoryMenu = MutableMenu(player.inventory.items())
    override val view: InventoryView
        get() = InventoryView(inventoryMenu, listOf(player.armor, player.weapon))

    override fun backAction(): SceneHandler? = null

    /**
     * Changes current inventory item.
     */
    override fun directionAction(direction: Direction): SceneHandler? {
        when (direction) {
            Direction.UP -> inventoryMenu.toPreviousItem()
            Direction.DOWN -> inventoryMenu.toNextItem()
        }
        return this
    }

    /**
     * Equips current item.
     */
    override fun selectAction(): SceneHandler? {
        if (inventoryMenu.size() != 0) {
            player.equip(inventoryMenu.currentItemIndex())
        }
        return this
    }
}