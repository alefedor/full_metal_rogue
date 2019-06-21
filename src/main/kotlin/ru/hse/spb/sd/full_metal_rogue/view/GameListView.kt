package ru.hse.spb.sd.full_metal_rogue.view

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.UIDrawerFactory
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.ascii.GameListSceneAsciiUIDrawer

/**
 * List of games available at server representation.
 */
class GameListView(val gameListMenu: MutableMenu<String>) : View {
    override fun draw(drawerFactory: UIDrawerFactory) {
        val drawer = drawerFactory.gameListSceneUIDrawer()
        drawer.clear()
        drawer.outputGamesList(gameListMenu.currentItemIndex(), gameListMenu.getItemsList())
    }
}