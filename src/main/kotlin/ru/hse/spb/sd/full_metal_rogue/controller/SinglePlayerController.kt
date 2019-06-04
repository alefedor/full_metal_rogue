package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.logic.map.FileMapLoader
import ru.hse.spb.sd.full_metal_rogue.logic.map.MutableGameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.playerPositions
import ru.hse.spb.sd.full_metal_rogue.view.DeathView
import ru.hse.spb.sd.full_metal_rogue.view.View
import ru.hse.spb.sd.full_metal_rogue.view.handler.DeathSceneHandler
import ru.hse.spb.sd.full_metal_rogue.view.handler.LevelSceneHandler
import java.awt.event.KeyEvent
import ru.hse.spb.sd.full_metal_rogue.controller.Controller as Controller

class SinglePlayerController(private val map: MutableGameMap) : Controller() {
    private val game = Game(map)

    init {
        game.join(PLAYER_NAME)
        drawView()
    }

    override fun handleKey(key: KeyEvent) {
        val command = mapKey(key) ?: return
        if (!wasDeath)
            game.makeTurn(PLAYER_NAME, command)
        drawView()
    }

    private fun drawView() {
        val view = if (wasDeath) null else game.getView(PLAYER_NAME)

        if (view == null) {
            // save and delete is available only in single player mode
            if (map.playerPositions().isEmpty())
                FileMapLoader.deleteMap()
            else
                FileMapLoader.saveMap(map)
        }

        checkView(view)

        GameState.gui.draw(view)
    }

    companion object {
        const val PLAYER_NAME = "You"
    }
}