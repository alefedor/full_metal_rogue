package ru.hse.spb.sd.full_metal_rogue.view.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.grpc.Client
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
            val controller = GameState.currentController

            GameState.currentController = MultiPlayerController(client, currentGameName, playerName)
            try {
                client.joinGame(currentGameName, playerName)
            } catch (e: StatusRuntimeException) {
                when (e.status.code) {
                    Status.Code.UNAVAILABLE -> showServerUnavailableMessage()
                    Status.Code.INVALID_ARGUMENT -> showErrorMessage(e.status.description ?: "Can't join this game.")
                    else -> showErrorMessage("Can't join this game")
                }
                GameState.currentController = controller
            }
        }
        return this
    }
}