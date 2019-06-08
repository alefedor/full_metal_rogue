package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.logic.Game
import ru.hse.spb.sd.full_metal_rogue.logic.map.FileMapLoader
import ru.hse.spb.sd.full_metal_rogue.logic.map.MutableGameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.player
import ru.hse.spb.sd.full_metal_rogue.logic.map.playerPositions
import ru.hse.spb.sd.full_metal_rogue.view.DeathView
import java.awt.event.KeyEvent
import ru.hse.spb.sd.full_metal_rogue.controller.Controller as Controller

/**
 * Game controller for single player mode.
 */
class SinglePlayerController(private val map: MutableGameMap) : Controller() {
    private val game = Game(map)

    init {
        game.join(PLAYER_NAME)
        drawView()
    }

    override fun handleKey(key: KeyEvent) {
        val command = mapKey(key) ?: return
        if (map.player(PLAYER_NAME).isAlive) {
            game.makeTurn(PLAYER_NAME, command)
        }
        // save and delete are available only in single player mode
        if (map.player(PLAYER_NAME).isDead) {
            FileMapLoader.deleteMap()
        }
        drawView()
    }

    private fun drawView() {
        val view = game.getView(PLAYER_NAME)
        drawView(view)
    }

    companion object {
        const val PLAYER_NAME = "You"
    }
}