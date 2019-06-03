package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Chest
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.view.ChestView
import ru.hse.spb.sd.full_metal_rogue.view.MutableMenu

/**
 * Handles user input on a ChestView.
 */
class ChestSceneHandler(chest: Chest, private val player: Player) : GameSceneHandler() {
    private val chestItems = MutableMenu(chest.items)
    override val view: ChestView
        get() = ChestView(chestItems)

    override fun backAction(): GameSceneHandler? = null

    /**
     * Changes current chest item.
     */
    override fun directionAction(playerName: String, direction: Direction): GameSceneHandler? {
        when(direction) {
            Direction.UP -> chestItems.toPreviousItem()
            Direction.DOWN -> chestItems.toNextItem()
        }
        return this
    }

    /**
     * Moves current item to player's inventory.
     */
    override fun selectAction(playerName: String): GameSceneHandler? {
        if (chestItems.size() != 0) {
            val currentItem = chestItems.currentItem()
            chestItems.removeCurrentItem()
            player.inventory.add(currentItem)
        }
        return this
    }
}
