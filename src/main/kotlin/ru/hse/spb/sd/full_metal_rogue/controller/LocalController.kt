package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.view.View
import ru.hse.spb.sd.full_metal_rogue.view.handler.SceneHandler
import ru.hse.spb.sd.full_metal_rogue.view.handler.StartSceneHandler
import java.awt.event.KeyEvent
import java.util.*

class LocalController : Controller {
    private val handlersStack = Stack<SceneHandler>()

    init {
        handlersStack.push(StartSceneHandler())
    }

    override fun handleKey(key: KeyEvent) {
        TODO("not implemented")
    }

    private fun sentCurrentView() {
        val currentView = handlersStack.peek().view
        GameState.gui.draw(currentView)
    }
}