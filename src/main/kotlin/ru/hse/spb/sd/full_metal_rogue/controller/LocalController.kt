package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.view.handler.SceneHandler
import ru.hse.spb.sd.full_metal_rogue.view.handler.StartSceneHandler
import java.awt.event.KeyEvent
import java.util.*

class LocalController : Controller() {
    private val handlersStack = Stack<SceneHandler>()

    init {
        handlersStack.push(StartSceneHandler())
        sentCurrentView()
    }

    override fun handleKey(key: KeyEvent) {
        when (val command = mapKey(key)) {
            SelectCommand -> handlersStack.peek().selectAction()
            BackCommand -> handlersStack.peek().backAction()
            is DirectionCommand -> handlersStack.peek().directionAction(command.direction)
        }
    }

    private fun sentCurrentView() {
        val currentView = handlersStack.peek().view
        GameState.gui.draw(currentView)
    }
}