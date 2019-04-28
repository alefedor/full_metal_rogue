package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Inventory
import ru.hse.spb.sd.full_metal_rogue.scene.InventoryScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer

class InventorySceneHandler(private val sceneDrawer: SceneDrawer,
                            private val inventory: Inventory
) : SceneHandler(sceneDrawer) {
    override val scene: InventoryScene
        get() = TODO("not implemented")
}