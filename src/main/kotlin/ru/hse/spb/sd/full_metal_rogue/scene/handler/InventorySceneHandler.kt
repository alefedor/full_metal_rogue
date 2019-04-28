package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.scene.InventoryScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer

/**
 * Handles user input on a InventoryScene.
 */
class InventorySceneHandler(private val player: Player,
                            private val sceneDrawer: SceneDrawer
) : SceneHandler(sceneDrawer) {/*
    val equipedItemsMenu
        get() = MutableMenu(listOf(player.weapon, player.armor))
    val inventoryItemsMenu
        get() = MutableMenu(player.inventory.items())
    val inventoryMenu = MutableMenu(listOf(equipedItemsMenu, inventoryItemsMenu))*/
    override val scene: InventoryScene
        get() = TODO("not implemented")


}