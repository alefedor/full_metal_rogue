package ru.hse.spb.sd.full_metal_rogue.view.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.grpc.Client
import ru.hse.spb.sd.full_metal_rogue.controller.MultiPlayerController
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.GameListView
import ru.hse.spb.sd.full_metal_rogue.view.MutableMenu
import javax.swing.JOptionPane

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
            try {
                client.joinGame(currentGameName, playerName)
            } catch (e: StatusRuntimeException) {
                if (e.status.code == Status.Code.UNAVAILABLE) {
                    showServerUnavailableMessage()
                }
            }
        }
        return this
    }
}