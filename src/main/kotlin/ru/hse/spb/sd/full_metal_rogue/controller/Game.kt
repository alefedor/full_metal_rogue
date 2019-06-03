package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.view.View

class Game(private val name: String) {
    val view: View
        get() = TODO()
    @Volatile
    var currentPlayer: String? = null

    val playerList = mutableListOf<String>()

    fun join(playerName: String) {

    }

    fun makeTurn(playerName: String, command: Command) {

    }
}