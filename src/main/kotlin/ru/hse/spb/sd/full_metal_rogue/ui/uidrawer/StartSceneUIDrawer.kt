package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer

import asciiPanel.AsciiPanel

class StartSceneUIDrawer(terminal: AsciiPanel) : UIDrawer(terminal) {
    val singlePlayerOptions = listOf("Continue", "Start a new game")
    val multiPlayerOptions = listOf("Join a running game", "Start a new game")
    val defaultMenuOptions = mutableListOf("Singleplayer", "Multiplayer")

    private val optionsLeftOffset = 15

    fun outputGameTitle() {
        outputMessageInCenter("Welcome to Full Metal Rogue.", -8)
    }

    fun outputHelpMessage() {
        val verticalOffset = (terminal.heightInCharacters - 8) - terminal.heightInCharacters / 2
        outputMessageInCenter("Press Esc to exit", verticalOffset)
        outputMessageInCenter("Press E to choose an option", verticalOffset + 1)
        outputMessageInCenter("Controls: W-A-S-D for player movement, Esc to exit", verticalOffset + 2)
    }

    fun outputMenuItems(currentPosition: Int, options: List<String>) {
        val pos = if (currentPosition < 0 || currentPosition >= options.size) 0 else currentPosition
        var offset = -5
        options.forEachIndexed { i, s ->
            if (i == pos) {
                outputMessageInCenter(s, offset, AsciiPanel.brightCyan)
            } else {
                outputMessageInCenter(s, offset)
            }
            offset++
        }
    }
}