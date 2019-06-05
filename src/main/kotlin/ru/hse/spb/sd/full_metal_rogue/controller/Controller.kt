package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.DeathView
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.awt.event.KeyEvent

/**
 * Represents a game controller, handling user input and calling GUI to output current view.
 */
abstract class Controller {
    protected var wasDeath = false

    /**
     * Handles key pressed by user.
     */
    abstract fun handleKey(key: KeyEvent)

    protected fun mapKey(key: KeyEvent): Command? = when (key.keyCode) {
        KeyEvent.VK_ESCAPE -> BackCommand
        KeyEvent.VK_W -> DirectionCommand(Direction.UP)
        KeyEvent.VK_S -> DirectionCommand(Direction.DOWN)
        KeyEvent.VK_A -> DirectionCommand(Direction.LEFT)
        KeyEvent.VK_D -> DirectionCommand(Direction.RIGHT)
        KeyEvent.VK_E -> SelectCommand
        else -> null
    }

    private fun checkView(view: View?) {
        if (view is DeathView)
            wasDeath = true
    }

    /**
     * Makes GUI show the given [view]. If died then call GUI method with null to reset Controller.
     */
    fun drawView(view: View?) {
        val shownView = if (wasDeath) null else view

        checkView(shownView)

        GameState.gui.draw(shownView)
    }
}