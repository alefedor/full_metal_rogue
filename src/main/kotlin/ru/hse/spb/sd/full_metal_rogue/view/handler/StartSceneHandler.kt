package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.controller.Client
import ru.hse.spb.sd.full_metal_rogue.controller.Game
import ru.hse.spb.sd.full_metal_rogue.controller.MultiPlayerController
import ru.hse.spb.sd.full_metal_rogue.controller.SinglePlayerController
import ru.hse.spb.sd.full_metal_rogue.logic.level.StandardLevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.logic.map.FileMapLoader
import ru.hse.spb.sd.full_metal_rogue.view.MutableMenu
import ru.hse.spb.sd.full_metal_rogue.view.StartView
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
        )
) : SceneHandler() {
    override val view
        get() = StartView(menu)

    private var host: String? = null
    private var gameName: String? = null

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
                StartSceneHandler(newMenu)
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
                host = createInputDialog("Input host name", "Server host")
                if (host == null) {
                     StartSceneHandler()
                } else {
                    val newMenu = MutableMenu(
                        mutableListOf(
                            MainMenuItem.MULTIPLAYER_JOIN,
                            MainMenuItem.MULTIPLAYER_NEW_GAME)
                    )
                    StartSceneHandler(newMenu)
                }
            }
            // change controller to the local controller that we previously created
            MainMenuItem.MULTIPLAYER_JOIN -> {
                /*val playerName = createInputDialog("Input player name", "Game player")
                (GameState.currentController as MultiPlayerController)
                val gameNamesList = mutableListOf<String>()
                GameListSceneHandler(gameNamesList)*/
                StartSceneHandler(menu)
            }
            MainMenuItem.MULTIPLAYER_NEW_GAME -> {
                gameName = createInputDialog("Input game name", "Game")
                if (host != null && gameName != null) {
                    GameState.currentController = MultiPlayerController(Client(gameName!!, host!!, true))
                }
                StartSceneHandler(menu)
            }
        }

    private fun createInputDialog(message: String, title: String) = JOptionPane.showInputDialog(
        null,
        message,
        title,
        JOptionPane.QUESTION_MESSAGE)

    enum class MainMenuItem {
        SINGLEPLAYER, SINGLEPLAYER_NEW_GAME, SINGLEPLAYER_CONTINUE, MULTIPLAYER, MULTIPLAYER_NEW_GAME, MULTIPLAYER_JOIN
    }
}