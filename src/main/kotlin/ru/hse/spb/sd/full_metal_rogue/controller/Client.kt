package ru.hse.spb.sd.full_metal_rogue.controller

import io.grpc.ManagedChannelBuilder
import ru.hse.spb.sd.full_metal_rogue.FullMetalRogueServerGrpc
import ru.hse.spb.sd.full_metal_rogue.Server
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction

private const val PORT = 10000

class Client(
    host: String
) {
    private val blockingStub: FullMetalRogueServerGrpc.FullMetalRogueServerBlockingStub
    private val asyncStub: FullMetalRogueServerGrpc.FullMetalRogueServerStub

    init {
        val channel = ManagedChannelBuilder.forAddress(host, PORT).usePlaintext().build()
        blockingStub = FullMetalRogueServerGrpc.newBlockingStub(channel)
        asyncStub = FullMetalRogueServerGrpc.newStub(channel)
    }

    fun joinGame(gameName: String, playerName: String) {
        val request = Server.SubscribeGameRequest.newBuilder()
            .setGameName(gameName)
            .setPlayerName(playerName)
            .build()
        blockingStub.subscribeGame(request)
    }

    fun getGameList(): List<String> {
        val request = Server.GameListRequest.newBuilder().build()
        return blockingStub.getGameList(request).gamesList
    }

    fun sendCommand(gameName:String, playerName: String, command: Command) {
        val serverCommand = when (command) {
            is DirectionCommand -> {
                when {
                    command.direction == Direction.UP -> Server.Command.UP
                    command.direction == Direction.DOWN -> Server.Command.DOWN
                    command.direction == Direction.LEFT -> Server.Command.LEFT
                    command.direction == Direction.RIGHT -> Server.Command.RIGHT
                    else -> null
                }
            }
            is SelectCommand -> Server.Command.SELECT
            is BackCommand -> Server.Command.BACK
        } ?: return
        val request = Server.Action.newBuilder()
            .setGameName(gameName)
            .setPlayerName(playerName)
            .setCommand(serverCommand)
            .build()
        blockingStub.sendCommand(request)

    }

    fun createGame(gameName: String) {
        val request = Server.CreateGameRequest.newBuilder()
            .setGameName(gameName)
            .build()
        blockingStub.createGame(request)
    }
}