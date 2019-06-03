package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.GameState
import java.awt.event.KeyEvent

class SinglePlayerController : Controller() {
    private val game = Game()

    init {
        game.join(PLAYER_NAME)
    }

    override fun handleKey(key: KeyEvent) {
        game.makeTurn(PLAYER_NAME, mapKey(key))
        GameState.gui.draw(game.view)
    }

    companion object {
        const val PLAYER_NAME = "You"
    }
}