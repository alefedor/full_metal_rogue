package ru.hse.spb.sd.full_metal_rogue

import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import javax.swing.JFrame

fun main(args: Array<String>) {
    val drawer = SceneDrawer()
    drawer.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    drawer.isVisible = true
}