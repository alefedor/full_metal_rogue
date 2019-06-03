package ru.hse.spb.sd.full_metal_rogue.controller

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.awt.event.KeyEvent

class MultiPlayerController(private val host: String, private val gameName: String) : Controller() {
    private val channel = ManagedChannelBuilder.forAddress(host, 10000)

    override fun handleKey(key: KeyEvent) {
        TODO("not implemented")
    }
}