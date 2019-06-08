package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.map.player
import ru.hse.spb.sd.full_metal_rogue.view.*
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.*

/**
 * Draws scenes in terminal.
 */
class SceneDrawer(private val terminal: AsciiPanel) {
    /**
     * Outputs a view to screen.
     */
    fun draw(view: View) {
        view.draw(terminal)
    }
}