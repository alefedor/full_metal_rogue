package ru.hse.spb.sd.full_metal_rogue.view

import asciiPanel.AsciiPanel
import java.io.Serializable

/**
 * Representation of any displayable screen.
 */
interface View : Serializable {
    fun draw(terminal: AsciiPanel)
}