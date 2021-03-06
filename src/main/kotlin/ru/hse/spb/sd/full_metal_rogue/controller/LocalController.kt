package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.view.handler.SceneHandler
import ru.hse.spb.sd.full_metal_rogue.view.handler.StartSceneHandler
import java.awt.event.KeyEvent
import java.util.*

/**
 * Local game controller used in main menu.
 */
class LocalController : Controller() {
    private val handlersStack = Stack<SceneHandler>()

    init {
        handlersStack.push(StartSceneHandler())
        sendCurrentView()
    }

    override fun handleKey(key: KeyEvent) {
        val nextHandler = when (val command = mapKey(key)) {
            SelectCommand -> handlersStack.peek().selectAction()
            BackCommand -> handlersStack.peek().backAction()
            is DirectionCommand -> handlersStack.peek().directionAction(command.direction)
            null -> return
        }
        if (nextHandler == null) {
            handlersStack.pop()
        } else if (nextHandler != handlersStack.peek()) {
            handlersStack.push(nextHandler)
        }
        if (this == GameState.currentController) {
            sendCurrentView()
        }
    }

    private fun sendCurrentView() {
        val currentView = handlersStack.peek().view
        GameState.gui.draw(currentView)
    }
}