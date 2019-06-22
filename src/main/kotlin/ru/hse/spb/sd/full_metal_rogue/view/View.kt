package ru.hse.spb.sd.full_metal_rogue.view

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.UIDrawerFactory
import java.io.Serializable

/**
 * Representation of any displayable screen.
 */
interface View : Serializable {
    fun draw(drawerFactory: UIDrawerFactory)
}