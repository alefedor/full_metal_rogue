package ru.hse.spb.sd.full_metal_rogue.controller

import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import ru.hse.spb.sd.full_metal_rogue.FullMetalRogueServerGrpc
import ru.hse.spb.sd.full_metal_rogue.Server

private const val PORT = 10000

class Client(
    gameName: String,
    host: String,
    shouldCreateNewGame: Boolean
) {
    private val blockingStub: FullMetalRogueServerGrpc.FullMetalRogueServerBlockingStub
    private val asyncStub: FullMetalRogueServerGrpc.FullMetalRogueServerStub

    init {
        val channel = ManagedChannelBuilder.forAddress(host, PORT).usePlaintext().build()
        blockingStub = FullMetalRogueServerGrpc.newBlockingStub(channel)
        asyncStub = FullMetalRogueServerGrpc.newStub(channel)
        if (shouldCreateNewGame) {
            createGame(gameName)
        }
    }

    fun joinGame(gameName: String, playerName: String) {
        val request = Server.SubscribeGameRequest.newBuilder()
            .setGameName(gameName)
            .setPlayerName(playerName)
            .build()
        val responseObserver = object : StreamObserver<Server.View> {
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
        asyncStub.subscribeGame(request, responseObserver)
    }

    fun getGameList(): List<String> {
        val request = Server.GameListRequest.newBuilder().build()
        // TODO possibly use blocking stub
        // blockingStub.getGameList(request)
        val responseObserver = object : StreamObserver<Server.GameListResponse> {
            override fun onNext(value: Server.GameListResponse?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCompleted() {
                return
            }

            override fun onError(t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
        asyncStub.getGameList(request, responseObserver)
        return emptyList()
    }

    // TODO which command... what's the parameter here
    fun sendCommand() {}

    private fun createGame(gameName: String) {
        val request = Server.CreateGameRequest.newBuilder()
            .setGameName(gameName)
            .build()
        val responseObserver = object : StreamObserver<Server.CreateGameResponse> {
            override fun onNext(value: Server.CreateGameResponse?) {
                // send game name to server
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onCompleted() {

            }

            override fun onError(t: Throwable?) {

            }
        }
        asyncStub.createGame(request, responseObserver)
    }
}