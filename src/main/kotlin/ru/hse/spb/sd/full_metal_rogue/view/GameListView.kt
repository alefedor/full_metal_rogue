package ru.hse.spb.sd.full_metal_rogue.view

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.GameListSceneUIDrawer

/**
 * List of games available at server representation.
 */
class GameListView(val gameListMenu: MutableMenu<String>) : View {
    override fun draw(terminal: AsciiPanel) {
        val drawer = GameListSceneUIDrawer(terminal)
        drawer.clear()
        drawer.outputGamesList(gameListMenu.currentItemIndex(), gameListMenu.getItemsList())
    }
}