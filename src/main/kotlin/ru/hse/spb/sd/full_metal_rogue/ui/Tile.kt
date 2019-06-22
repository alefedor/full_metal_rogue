package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import java.awt.Color

/**
 * Represents a tile corresponding to a game object.
 */
enum class Tile(val glyph: Char, val color: Color) {
    FREE_SPACE('.', AsciiPanel.green),
    WALL('#', AsciiPanel.magenta),
    PLAYER('@', AsciiPanel.brightWhite),
    CHEST(10.toChar(), AsciiPanel.yellow),
    OTHER_PLAYER('@', AsciiPanel.brightRed)
}