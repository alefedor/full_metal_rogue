package ru.hse.spb.sd.full_metal_rogue.controller

import java.awt.event.KeyEvent

class SinglePlayerController : Controller() {
    private val game = Game()

    override fun handleKey(key: KeyEvent) {

    }

    companion object {
        const val PLAYER_NAME = "You"
    }
}