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
        /*val responseObserver = object : StreamObserver<Server.View> {
            override fun onNext(value: Server.View?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCompleted() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onError(t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        asyncStub.subscribeGame(request, responseObserver)*/
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
        // this obviously needs to be sent via an async stub, this is just for testing
        blockingStub.sendCommand(request)

    }

    fun createGame(gameName: String) {
        val request = Server.CreateGameRequest.newBuilder()
            .setGameName(gameName)
            .build()
        blockingStub.createGame(request)
        /*val responseObserver = object : StreamObserver<Server.CreateGameResponse> {
            override fun onNext(value: Server.CreateGameResponse?) {
                // send game name to server
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCompleted() {

            }

            override fun onError(t: Throwable?) {

            }
        }
        asyncStub.createGame(request, responseObserver)*/
    }
}