package ru.hse.spb.sd.full_metal_rogue.view.handler

import io.grpc.Status
import io.grpc.StatusRuntimeException
import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.controller.SinglePlayerController
import ru.hse.spb.sd.full_metal_rogue.grpc.Client
import ru.hse.spb.sd.full_metal_rogue.logic.level.StandardLevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.logic.map.FileMapLoader
import ru.hse.spb.sd.full_metal_rogue.view.MutableMenu
import ru.hse.spb.sd.full_metal_rogue.view.StartView
import java.lang.IllegalStateException
import javax.swing.JOptionPane
import kotlin.system.exitProcess

/**
 * Handles user input on a StartView
 */
class StartSceneHandler(
    private val menu: MutableMenu<MainMenuItem> =
        MutableMenu(
            mutableListOf(
                MainMenuItem.SINGLEPLAYER,
                MainMenuItem.MULTIPLAYER
            )
        ),
    private val host: String? = null
) : SceneHandler() {
    override val view
        get() = StartView(menu)

    /**
     * Exits the game.
     */
    override fun backAction() =
        if (view.isSinglePlayerMenu() || view.isMultiPlayerMenu()) {
            StartSceneHandler()
        } else {
            exitProcess(0)
        }

    /**
     * Changes current main menu item.
     */
    override fun directionAction(direction: Direction): SceneHandler? {
        when (direction) {
            Direction.UP -> menu.toPreviousItem()
            Direction.DOWN -> menu.toNextItem()
            else -> throw IllegalStateException("Direction in menu should be up or down")
        }
        return this
    }

    /**
     * Selects current main menu item.
     */
    override fun selectAction(): SceneHandler? =
        when (view.mainMenu.currentItem()) {
            MainMenuItem.SINGLEPLAYER -> {
                val newMenu = MutableMenu(
                    mutableListOf(
                        MainMenuItem.SINGLEPLAYER_CONTINUE,
                        MainMenuItem.SINGLEPLAYER_NEW_GAME)
                )
                StartSceneHandler(newMenu, this.host)
            }
            MainMenuItem.SINGLEPLAYER_NEW_GAME -> {
                GameState.currentController = SinglePlayerController(StandardLevelGenerator().generateLevel())
                this
            }
            MainMenuItem.SINGLEPLAYER_CONTINUE -> {
                val map = FileMapLoader.loadMap()
                if (map != null) {
                    GameState.currentController = SinglePlayerController(map)
                }
                this
            }
            MainMenuItem.MULTIPLAYER -> {
                val host = createInputDialog("Input host name", "Server host")
                if (host == null) {
                     StartSceneHandler()
                } else {
                    val newMenu = MutableMenu(
                        mutableListOf(
                            MainMenuItem.MULTIPLAYER_JOIN,
                            MainMenuItem.MULTIPLAYER_NEW_GAME)
                    )
                    StartSceneHandler(newMenu, host)
                }
            }
            MainMenuItem.MULTIPLAYER_JOIN -> {
                val playerName = createInputDialog("Input player name", "Game player")
                if (playerName != null && !checkPlayerName(playerName)) {
                    showErrorMessage("Incorrect player name. " +
                            "A valid name consists of  up to 10 latin symbols, digits, or underscores.")
                    StartSceneHandler(menu, host)
                } else {
                    if (host != null && playerName != null) {
                        val client = Client(host)
                        try {
                            GameListSceneHandler(client, playerName)
                        } catch (e: StatusRuntimeException) {
                            showServerUnavailableMessage()
                            StartSceneHandler(menu, host)
                        }
                    } else {
                        StartSceneHandler(menu, host)
                    }
                }
            }
            MainMenuItem.MULTIPLAYER_NEW_GAME -> {
                val gameName = createInputDialog("Input game name", "Game")
                if (host != null && gameName != null) {
                    val client = Client(host)
                    try {
                        client.createGame(gameName)
                    } catch (e: StatusRuntimeException) {
                        when (e.status.code) {
                            Status.Code.UNAVAILABLE -> showServerUnavailableMessage()
                            Status.Code.INVALID_ARGUMENT -> showErrorMessage(e.status.description ?:
                                "Can't create a game with this name.")
                            else -> showErrorMessage("Can't create a game with this name.")
                        }
                    }
                }
                StartSceneHandler(menu, host)
            }
        }

    private fun createInputDialog(message: String, title: String) = JOptionPane.showInputDialog(
        null,
        message,
        title,
        JOptionPane.QUESTION_MESSAGE)

    private fun checkPlayerName(playerName: String) =
        playerName.length <= 10 && playerName.matches(Regex("[A-Za-z0-9_]+"))

    enum class MainMenuItem {
        SINGLEPLAYER, SINGLEPLAYER_NEW_GAME, SINGLEPLAYER_CONTINUE, MULTIPLAYER, MULTIPLAYER_NEW_GAME, MULTIPLAYER_JOIN
    }
}