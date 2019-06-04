package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.grpc.Client
import java.awt.event.KeyEvent

class MultiPlayerController(val client: Client, val gameName: String, val playerName: String) : Controller() {
    override fun handleKey(key: KeyEvent) {
        val command = mapKey(key) ?: return

        if (!wasDeath)
            client.sendCommand(gameName, playerName, command)
    }
}