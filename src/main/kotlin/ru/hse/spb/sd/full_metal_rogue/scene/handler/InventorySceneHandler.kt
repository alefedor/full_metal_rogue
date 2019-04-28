package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.scene.InventoryScene
import ru.hse.spb.sd.full_metal_rogue.scene.MutableMenu
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer

/**
 * Handles user input on a InventoryScene.
 */
class InventorySceneHandler(private val player: Player,
                            sceneDrawer: SceneDrawer
) : SceneHandler(sceneDrawer) {
    private val inventoryMenu = MutableMenu(player.inventory.items())
    override val scene: InventoryScene
        get() = InventoryScene(inventoryMenu, listOf(player.armor, player.weapon))

    override fun backAction(): SceneHandler? = null

    /**
     * Changes current inventory item.
     */
    override fun directionAction(direction: Direction): SceneHandler? {
        when(direction) {
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