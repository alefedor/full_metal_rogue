package ru.hse.spb.sd.full_metal_rogue

import javax.swing.JFrame

fun main() {
    val game = Game()
    game.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    game.isVisible = true
}