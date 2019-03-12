package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import java.awt.Color

enum class Tile(val glyph: Char, val color: Color) {
    FREESPACE('%', AsciiPanel.green),
    WALL('$', AsciiPanel.brightMagenta),
    BOUNDS('|', AsciiPanel.brightCyan)
}