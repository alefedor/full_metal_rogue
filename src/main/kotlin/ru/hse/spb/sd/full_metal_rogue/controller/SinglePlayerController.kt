package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.logic.Game
import ru.hse.spb.sd.full_metal_rogue.logic.map.FileMapLoader
import ru.hse.spb.sd.full_metal_rogue.logic.map.MutableGameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.player
import java.awt.event.KeyEvent
import ru.hse.spb.sd.full_metal_rogue.controller.Controller as Controller

/**
 * Game controller for single player mode.
 */
class SinglePlayerController(private val map: MutableGameMap) : Controller() {
    private val game = Game(map)

    init {
        game.join(PLAYER_NAME)
        drawView(game.getView(PLAYER_NAME))
    }

    override fun handleKey(key: KeyEvent) {
        val command = mapKey(key) ?: return
        if (game.isAlive(PLAYER_NAME))
            game.makeTurn(PLAYER_NAME, command)

        val isDead = !game.isAlive(PLAYER_NAME)

        // save and load are available only in single player mode
        if (isDead)
            FileMapLoader.deleteMap()
        else
            FileMapLoader.saveMap(map) // auto save every turn

        drawView(game.getView(PLAYER_NAME))
    }

    companion object {
        const val PLAYER_NAME = "You"
    }
}