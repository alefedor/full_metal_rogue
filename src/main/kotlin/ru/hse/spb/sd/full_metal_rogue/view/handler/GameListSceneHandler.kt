package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.GameListView
import ru.hse.spb.sd.full_metal_rogue.view.MutableMenu

/**
 * Handles user input on a GameListSceneHandler.
 */
class GameListSceneHandler(gameNames: MutableList<String>) : SceneHandler() {
    private val gameNamesMenu = MutableMenu(gameNames)
    override val view: GameListView
        get() = GameListView(gameNamesMenu)

    override fun backAction(): SceneHandler? = null

    /**
     * Changes current game name.
     */
    override fun directionAction(direction: Direction): SceneHandler? {
        when (direction) {
            Direction.UP -> gameNamesMenu.toPreviousItem()
            Direction.DOWN -> gameNamesMenu.toNextItem()
        }
        return this
    }

    /**
     * Chooses current game.
     */
    override fun selectAction(): SceneHandler? {
        if (gameNamesMenu.size() != 0) {
            // TODO choose game
            // player.equip(inventoryMenu.currentItemIndex())
        }
        return this
    }
}