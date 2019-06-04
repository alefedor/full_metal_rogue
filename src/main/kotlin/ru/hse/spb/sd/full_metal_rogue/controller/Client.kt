package ru.hse.spb.sd.full_metal_rogue.controller

import io.grpc.ManagedChannelBuilder
import ru.hse.spb.sd.full_metal_rogue.FullMetalRogueServerGrpc

private const val PORT = 10000

class Client(
    private val playerName: String,
    private val gameName: String,
    host: String
) {
    private val stub: FullMetalRogueServerGrpc.FullMetalRogueServerStub

    init {
        val channel = ManagedChannelBuilder.forAddress(host, PORT).usePlaintext().build()
        stub = FullMetalRogueServerGrpc.newStub(channel)
    }
}