package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.controller.Client
import ru.hse.spb.sd.full_metal_rogue.controller.MultiPlayerController
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.GameListView
import ru.hse.spb.sd.full_metal_rogue.view.MutableMenu

/**
 * Handles user input on a GameListSceneHandler.
 */
class GameListSceneHandler(private val client: Client, private val playerName: String) : SceneHandler() {
    private val gameNamesMenu = MutableMenu(client.getGameList().toMutableList())
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
            else -> return this
        }
        return this
    }

    /**
     * Chooses current game.
     */
    override fun selectAction(): SceneHandler? {
        if (gameNamesMenu.size() != 0) {
            val currentGameName = gameNamesMenu.currentItem()
            GameState.currentController = MultiPlayerController(client, currentGameName, playerName)
            client.joinGame(currentGameName, playerName)
        }
        return this
    }
}