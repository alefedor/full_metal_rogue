package ru.hse.spb.sd.full_metal_rogue

import ru.hse.spb.sd.full_metal_rogue.controller.Controller
import ru.hse.spb.sd.full_metal_rogue.controller.LocalController
import ru.hse.spb.sd.full_metal_rogue.ui.GUI
import javax.swing.JFrame

object GameState {
    val gui = GUI()
    var currentController: Controller = LocalController()
}

fun main() {
    GameState.gui.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    GameState.gui.isVisible = true
}