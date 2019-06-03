package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.view.View
import ru.hse.spb.sd.full_metal_rogue.view.command.Command

class Game(private val name: String) {
    val view: View
        get() = TODO()
    @Volatile
    var currentPlayer: String? = null

    fun join(playerName: String) {

    }

    fun makeTurn(playerName: String, command: Command) {

    }
}