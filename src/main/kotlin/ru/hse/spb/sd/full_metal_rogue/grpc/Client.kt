package ru.hse.spb.sd.full_metal_rogue.grpc

import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import ru.hse.spb.sd.full_metal_rogue.FullMetalRogueServerGrpc
import ru.hse.spb.sd.full_metal_rogue.GameState
import ru.hse.spb.sd.full_metal_rogue.Server
import ru.hse.spb.sd.full_metal_rogue.controller.*
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream
import java.util.concurrent.Phaser

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

        val barrier = Phaser(2)

        asyncStub.subscribeGame(request, object : StreamObserver<Server.View> {
            var wasView = false

            override fun onCompleted() {
                // nothing to do
            }

            override fun onError(t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onNext(view: Server.View) {
                val view = protoViewToGameView(view)
                GameState.gui.draw(view)

                if (!wasView) {
                    barrier.arrive()
                    wasView = true
                }
            }
        })

        barrier.arriveAndAwaitAdvance()
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

    private fun protoViewToGameView(view: Server.View): View? {
        val bytes = view.bytes.toByteArray()
        val gameView = ObjectInputStream(ByteArrayInputStream(bytes)).use { it.readObject() }
        return gameView as View?
    }
}