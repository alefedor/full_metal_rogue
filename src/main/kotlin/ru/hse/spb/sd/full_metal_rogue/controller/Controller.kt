package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.DeathView
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.awt.event.KeyEvent

abstract class Controller {
    protected var wasDeath = false

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

    protected fun checkView(view: View?) {
        if (view is DeathView)
            wasDeath = true
    }
}