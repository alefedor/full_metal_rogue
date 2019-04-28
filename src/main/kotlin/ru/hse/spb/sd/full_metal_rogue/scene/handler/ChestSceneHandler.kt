package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.logic.objects.Chest
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.scene.ChestScene
import ru.hse.spb.sd.full_metal_rogue.scene.MutableMenu
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer

class ChestSceneHandler(private val chest: Chest,
                        private val player: Player,
                        private val sceneDrawer: SceneDrawer
) : SceneHandler(sceneDrawer) {
    private val chestItems = MutableMenu(chest.items)
    override val scene: ChestScene
        get() = TODO("not implemented")

}
