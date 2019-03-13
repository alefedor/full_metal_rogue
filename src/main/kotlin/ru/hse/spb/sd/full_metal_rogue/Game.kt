package ru.hse.spb.sd.full_metal_rogue

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.scene.handler.SceneHandler
import ru.hse.spb.sd.full_metal_rogue.scene.handler.StartSceneHandler
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import javax.swing.JFrame

private const val WINDOW_HEIGHT = 40
private const val WINDOW_WIDTH = 100

class Game : JFrame(), KeyListener {
    private val scenesStack = Stack<SceneHandler>()

    init {
        val terminal = AsciiPanel(WINDOW_WIDTH, WINDOW_HEIGHT)
        add(terminal)
        pack()
        addKeyListener(this)
        val sceneDrawer = SceneDrawer(terminal)
        scenesStack.push(StartSceneHandler(sceneDrawer))
        repaint()
    }

    override fun repaint() {
        scenesStack.peek().repaint()
        super.repaint()
    }

    override fun keyPressed(key: KeyEvent) {
        val nextScene = scenesStack.peek().handleUserInput(key)
        if (nextScene == null) {
            scenesStack.pop()
        } else if(nextScene != scenesStack.peek()) {
            scenesStack.push(nextScene)
        }
        repaint()
    }

    override fun keyTyped(p0: KeyEvent?) {
    }

    override fun keyReleased(p0: KeyEvent?) {
    }
}