package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.ascii

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.StartSceneUIDrawer

/**
 * Handles writing to terminal for StartView.
 */
class StartSceneAsciiUIDrawer(terminal: AsciiPanel) : AsciiUIDrawer(terminal), StartSceneUIDrawer {
    /**
     * Outputs game title.
     */
    override fun outputWelcomeMessage() {
        outputMessageInCenter("Welcome to Full Metal Rogue.", -8)
    }

    /**
     * Outputs game controls hints.
     */
    override fun outputHelpMessage() {
        val verticalOffset = (terminal.heightInCharacters - 8) - terminal.heightInCharacters / 2
        outputMessageInCenter("Press Esc to exit", verticalOffset)
        outputMessageInCenter("Press E to choose an option", verticalOffset + 1)
        outputMessageInCenter("Controls: W-A-S-D for player movement, Esc to exit", verticalOffset + 2)
    }

    /**
     * Outputs start scene menu.
     */
    override fun outputMenuItems(currentPosition: Int, options: List<String>) {
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