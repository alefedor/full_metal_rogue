package ru.hse.spb.sd.full_metal_rogue

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.command.*
import ru.hse.spb.sd.full_metal_rogue.view.handler.SceneHandler
import ru.hse.spb.sd.full_metal_rogue.view.handler.StartSceneHandler
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import javax.swing.JFrame

private const val WINDOW_HEIGHT = 40
private const val WINDOW_WIDTH = 100

/**
 * Main game class.
 */
class Game : JFrame() {
    private val scenesStack = Stack<SceneHandler>()

    init {
        val terminal = AsciiPanel(WINDOW_WIDTH, WINDOW_HEIGHT)
        add(terminal)
        pack()
        addKeyListener(KeyBinder())
        val sceneDrawer = SceneDrawer(terminal)
        scenesStack.push(StartSceneHandler(sceneDrawer))
        repaint()
    }

    /**
     * Updates screen content.
     */
    override fun repaint() {
        scenesStack.peek().repaint()
        super.repaint()
    }

    /**
     * Handles command.
     */
    fun executeCommand(command: Command) {
        val nextScene = command.execute()
        if (nextScene == null) {
            scenesStack.pop()
        } else if (nextScene != scenesStack.peek()) {
            scenesStack.push(nextScene)
        }
        repaint()
    }

    /**
     * Listens to user input and binds keys to commands,
     */
    private inner class KeyBinder : KeyListener {
        /**
         * Maps input key to command and passes it to [Game]
         */
        override fun keyPressed(key: KeyEvent) {
            val command = when (key.keyCode) {
                KeyEvent.VK_ESCAPE -> BackCommand(scenesStack.peek())
                KeyEvent.VK_W -> DirectionCommand(scenesStack.peek(), Direction.UP)
                KeyEvent.VK_S -> DirectionCommand(scenesStack.peek(), Direction.DOWN)
                KeyEvent.VK_A -> DirectionCommand(scenesStack.peek(), Direction.LEFT)
                KeyEvent.VK_D -> DirectionCommand(scenesStack.peek(), Direction.RIGHT)
                KeyEvent.VK_E -> SelectCommand(scenesStack.peek())
                else -> IdleCommand(scenesStack.peek())
            }
            executeCommand(command)
        }

        override fun keyTyped(key: KeyEvent) {
        }

        override fun keyReleased(key: KeyEvent) {
        }
    }
}