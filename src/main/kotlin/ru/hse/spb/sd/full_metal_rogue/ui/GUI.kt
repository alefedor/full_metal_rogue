package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.controller.LocalController
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.UIDrawerFactory
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.ascii.AsciiUIDrawerFactory
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import javax.swing.JFrame

private const val WINDOW_HEIGHT = 40
private const val WINDOW_WIDTH = 100

class GUI : JFrame() {
    private val drawerFactory: UIDrawerFactory

    init {
        val terminal = AsciiPanel(WINDOW_WIDTH, WINDOW_HEIGHT)
        add(terminal)
        pack()
        addKeyListener(object : KeyListener {
            override fun keyPressed(key: KeyEvent) {
                GameState.currentController.handleKey(key)
            }

            override fun keyTyped(key: KeyEvent) {}

            override fun keyReleased(key: KeyEvent) {}
        })

        drawerFactory = AsciiUIDrawerFactory(terminal)
    }

    /**
     * Draws a view. Replaces the controller with LocalController if necessary.
     */
    fun draw(view: View?) {
        if (view == null) { // should reset controller
            GameState.currentController = LocalController()
            return
        }

        view.draw(drawerFactory)
        super.repaint()
    }
}