package ru.hse.spb.sd.full_metal_rogue.view

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
}