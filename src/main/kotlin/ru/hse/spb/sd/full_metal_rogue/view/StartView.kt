package ru.hse.spb.sd.full_metal_rogue.view

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.StartSceneUIDrawer
import ru.hse.spb.sd.full_metal_rogue.view.handler.StartSceneHandler

/**
 * Representation of main menu.
 */
class StartView(val mainMenu: MutableMenu<StartSceneHandler.MainMenuItem>) : View {
    fun isSinglePlayerMenu() =
        mainMenu.getItemsList().contains(StartSceneHandler.MainMenuItem.SINGLEPLAYER_NEW_GAME) &&
                mainMenu.getItemsList().contains(StartSceneHandler.MainMenuItem.SINGLEPLAYER_CONTINUE)

    fun isMultiPlayerMenu() =
        mainMenu.getItemsList().contains(StartSceneHandler.MainMenuItem.MULTIPLAYER_NEW_GAME) &&
                mainMenu.getItemsList().contains(StartSceneHandler.MainMenuItem.MULTIPLAYER_JOIN)

    override fun draw(terminal: AsciiPanel) {
        val drawer = StartSceneUIDrawer(terminal)
        drawer.clear()
        drawer.outputsWelcomeMessage()
        when {
            isSinglePlayerMenu() -> {
                drawer.outputMenuItems(mainMenu.currentItemIndex(), drawer.singlePlayerOptions)
            }
            isMultiPlayerMenu() -> {
                drawer.outputMenuItems(mainMenu.currentItemIndex(), drawer.multiPlayerOptions)
            }
            else -> {
                drawer.outputMenuItems(mainMenu.currentItemIndex(), drawer.defaultMenuOptions)
            }
        }
        drawer.outputHelpMessage()
    }
}