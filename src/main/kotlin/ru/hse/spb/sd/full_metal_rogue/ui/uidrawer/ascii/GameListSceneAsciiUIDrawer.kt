package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.ascii

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.GameListSceneUIDrawer

/**
 * Handles writing to terminal for GameListView.
 */
class GameListSceneAsciiUIDrawer(terminal: AsciiPanel) : AsciiUIDrawer(terminal), GameListSceneUIDrawer {
    private val gamesListLeftOffset = 5
    private val verticalOffset = 2

    /**
     * Outputs available games menu and a matching title.
     */
    override fun outputGamesList(currentPosition: Int, gameNames: List<String>) {
        outputHeader("Available games")
        for (i in 0 until gameNames.size) {
            if (i == currentPosition) {
                terminal.write(gameNames[i], gamesListLeftOffset, verticalOffset + i, AsciiPanel.brightCyan)
            } else {
                terminal.write(gameNames[i], gamesListLeftOffset, verticalOffset + i)
            }
        }
    }
}