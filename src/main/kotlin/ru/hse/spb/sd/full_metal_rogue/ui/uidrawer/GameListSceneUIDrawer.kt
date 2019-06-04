package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer

import asciiPanel.AsciiPanel

class GameListSceneUIDrawer(terminal: AsciiPanel) : UIDrawer(terminal) {
    private val gamesListLeftOffset = 5
    private val verticalOffset = 2

    fun outputGamesList(currentPosition: Int, gameNames: List<String>) {
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