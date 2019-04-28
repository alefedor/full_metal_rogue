package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Chest
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.scene.ChestScene
import ru.hse.spb.sd.full_metal_rogue.scene.MutableMenu
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer

class ChestSceneHandler(private val chest: Chest,
                        private val player: Player,
                        sceneDrawer: SceneDrawer
) : SceneHandler(sceneDrawer) {
    private val chestItems
        get() = MutableMenu(chest.items)
    override val scene: ChestScene
        get() = ChestScene(chestItems)

    override fun backAction(): SceneHandler? = null

    /**
     * Changes current chest item.
     */
    override fun directionAction(direction: Direction): SceneHandler? {
        when(direction) {
            Direction.UP -> chestItems.toNextItem()
            Direction.DOWN -> chestItems.toPreviousItem()
        }
        return this
    }

    /**
     * Moves current item to player's inventory.
     */
    override fun selectAction(): SceneHandler? {
        if (chestItems.size() != 0) {
            val currentItem = chestItems.currentItem()
            player.inventory.add(currentItem)
            chest.items.removeAt(chestItems.currentItemIndex())
        }
        return this
    }
}
