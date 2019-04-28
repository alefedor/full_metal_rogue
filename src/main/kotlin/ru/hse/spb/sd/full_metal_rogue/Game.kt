package ru.hse.spb.sd.full_metal_rogue

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.scene.command.*
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
    private val keyBinder = KeyBinder { scenesStack.peek() }

    init {
        val terminal = AsciiPanel(WINDOW_WIDTH, WINDOW_HEIGHT)
        add(terminal)
        pack()
        addKeyListener(this)
        val sceneDrawer = SceneDrawer(terminal)
        scenesStack.push(StartSceneHandler(sceneDrawer))
        repaint()
    }

    companion object {
        const val SAVE_NAME = "save.txt"
    }

    override fun repaint() {
        scenesStack.peek().repaint()
        super.repaint()
    }

    override fun keyPressed(key: KeyEvent) {
        val nextScene = keyBinder.getCommand(key).execute()
        if (nextScene == null) {
            scenesStack.pop()
        } else if (nextScene != scenesStack.peek()) {
            scenesStack.push(nextScene)
        }
        repaint()
    }

    override fun keyTyped(p0: KeyEvent?) {
    }

    override fun keyReleased(p0: KeyEvent?) {
    }
}

private class KeyBinder(private val scene: () -> SceneHandler) {
    fun getCommand(key: KeyEvent): Command =
        when (key.keyCode) {
            KeyEvent.VK_ESCAPE -> BackCommand(scene)
            KeyEvent.VK_W -> DirectionCommand(scene, Direction.UP)
            KeyEvent.VK_S -> DirectionCommand(scene, Direction.DOWN)
            KeyEvent.VK_A -> DirectionCommand(scene, Direction.LEFT)
            KeyEvent.VK_D -> DirectionCommand(scene, Direction.RIGHT)
            KeyEvent.VK_E -> SelectCommand(scene)
            else -> IdleCommand(scene)
        }
}