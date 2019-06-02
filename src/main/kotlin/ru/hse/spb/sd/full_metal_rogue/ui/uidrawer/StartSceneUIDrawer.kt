package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer

import asciiPanel.AsciiPanel

class StartSceneUIDrawer(terminal: AsciiPanel) : UIDrawer(terminal) {
    /**
     * Outputs the greeting message.
     */
    fun outputStartMessage(currentPosition: Int) {
        val options = listOf("Continue", "Start a new game")
        val pos = if (currentPosition < 0 || currentPosition >= options.size) 0 else currentPosition

        outputMessageInCenter("Welcome to Full Metal Rogue.", -4)
        var offset = -2
        options.forEachIndexed { i, s ->
            if (i == pos) {
                outputMessageInCenter(s, offset, AsciiPanel.brightCyan)
            } else {
                outputMessageInCenter(s, offset)
            }
            offset++
        }
        offset += 2
        outputMessageInCenter("Press Esc to exit", offset)
        outputMessageInCenter("Press E to choose an option", offset + 1)
        outputMessageInCenter("Controls: W-A-S-D for player movement, Esc to exit", offset + 2)
    }
}