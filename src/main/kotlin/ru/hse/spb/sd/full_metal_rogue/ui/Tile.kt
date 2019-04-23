package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import java.awt.Color

enum class Tile(val glyph: Char, val color: Color) {
    FREE_SPACE('.', AsciiPanel.green),
    WALL('#', AsciiPanel.magenta),
    PLAYER('@', AsciiPanel.brightWhite),
    CHEST('□', AsciiPanel.yellow)
}