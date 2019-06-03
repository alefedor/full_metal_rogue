package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.logic.map.MutableGameMap
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.awt.event.KeyEvent
import ru.hse.spb.sd.full_metal_rogue.controller.Controller as Controller

class SinglePlayerController(map: MutableGameMap) : Controller() {
    private val game = Game(map)

    init {
        game.join(PLAYER_NAME)
        drawView()
    }

    override fun handleKey(key: KeyEvent) {
        val command = mapKey(key) ?: return
        game.makeTurn(PLAYER_NAME, command)
        drawView()
    }

    private fun drawView() {
        val view = game.view
        if (view == null) {
            GameState.currentController = LocalController()
        } else {
            GameState.gui.draw(view)
        }
    }

    companion object {
        const val PLAYER_NAME = "You"
    }
}