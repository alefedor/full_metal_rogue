package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.controller.LocalController
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame

private const val WINDOW_HEIGHT = 40
private const val WINDOW_WIDTH = 100

class GUI : JFrame() {
    private val drawer: SceneDrawer

    init {
        val terminal = AsciiPanel(WINDOW_WIDTH, WINDOW_HEIGHT)
        add(terminal)
        pack()
        addKeyListener(object : KeyListener {
            override fun keyPressed(key: KeyEvent) {
                GameState.currentController.innerHandleKey(key)
            }

            override fun keyTyped(key: KeyEvent) {}

            override fun keyReleased(key: KeyEvent) {}
        })
        drawer = SceneDrawer(terminal)
    }

    fun draw(view: View?) {
        if (view == null) { // should reset controller
            GameState.currentController = LocalController()
            return
        }

        drawer.draw(view)
        super.repaint()
    }
}