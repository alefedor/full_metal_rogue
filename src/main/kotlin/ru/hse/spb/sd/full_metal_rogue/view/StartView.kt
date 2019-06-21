package ru.hse.spb.sd.full_metal_rogue.view

import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.UIDrawerFactory
import ru.hse.spb.sd.full_metal_rogue.view.handler.StartSceneHandler

/**
 * Representation of main menu.
 */
class StartView(val mainMenu: MutableMenu<StartSceneHandler.MainMenuItem>) : View {
    companion object {
        private val SINGLE_PLAYER_OPTIONS = listOf("Continue", "Start a new game")
        private val MULTI_PLAYER_OPTIONS = listOf("Join a running game", "Start a new game")
        private val DEFAULT_MENU_OPTIONS = mutableListOf("Singleplayer", "Multiplayer")
    }

    fun isSinglePlayerMenu() =
        mainMenu.getItemsList().contains(StartSceneHandler.MainMenuItem.SINGLEPLAYER_NEW_GAME) &&
                mainMenu.getItemsList().contains(StartSceneHandler.MainMenuItem.SINGLEPLAYER_CONTINUE)

    fun isMultiPlayerMenu() =
        mainMenu.getItemsList().contains(StartSceneHandler.MainMenuItem.MULTIPLAYER_NEW_GAME) &&
                mainMenu.getItemsList().contains(StartSceneHandler.MainMenuItem.MULTIPLAYER_JOIN)

    override fun draw(drawerFactory: UIDrawerFactory) {
        val drawer = drawerFactory.startSceneUIDrawer()
        drawer.clear()
        drawer.outputWelcomeMessage()
        when {
            isSinglePlayerMenu() -> {
                drawer.outputMenuItems(mainMenu.currentItemIndex(), SINGLE_PLAYER_OPTIONS)
            }
            isMultiPlayerMenu() -> {
                drawer.outputMenuItems(mainMenu.currentItemIndex(), MULTI_PLAYER_OPTIONS)
            }
            else -> {
                drawer.outputMenuItems(mainMenu.currentItemIndex(), DEFAULT_MENU_OPTIONS)
            }
        }
        drawer.outputHelpMessage()
    }
}