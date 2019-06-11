package ru.hse.spb.sd.full_metal_rogue.grpc

import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import ru.hse.spb.sd.full_metal_rogue.FullMetalRogueServerGrpc
import ru.hse.spb.sd.full_metal_rogue.GameState.currentController
import ru.hse.spb.sd.full_metal_rogue.Server
import ru.hse.spb.sd.full_metal_rogue.controller.*
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.io.ByteArrayInputStream
import java.io.ObjectInputStream
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

private const val PORT = 10000

/**
 * Represents a gRPC client.
 * A client can create a new game, view all available games,
 * join an existing game, and send commands to a game.
 */
class Client(
    host: String
) {
    private val blockingStub: FullMetalRogueServerGrpc.FullMetalRogueServerBlockingStub
    private val asyncStub: FullMetalRogueServerGrpc.FullMetalRogueServerStub

    @Volatile
    private var initializationFinished = false

    init {
        val channel = ManagedChannelBuilder.forAddress(host, PORT).usePlaintext().build()
        blockingStub = FullMetalRogueServerGrpc.newBlockingStub(channel)
        asyncStub = FullMetalRogueServerGrpc.newStub(channel)
    }

    /**
     * Allows player [playerName] join game [gameName].
     */
    fun joinGame(gameName: String, playerName: String) {
        val request = Server.SubscribeGameRequest.newBuilder()
            .setGameName(gameName)
            .setPlayerName(playerName)
            .build()

        val initializationLock = ReentrantLock()
        val initializationCondition = initializationLock.newCondition()

        var exception: Throwable? = null

        asyncStub.subscribeGame(request, object : StreamObserver<Server.View> {
            override fun onCompleted() {
                // nothing to do
            }

            override fun onError(t: Throwable?) {
                if (!initializationFinished) {
                    exception = t

                    if (!initializationFinished)
                        finishInitialization()
                } else {
                    t?.printStackTrace()
                }
            }

            override fun onNext(view: Server.View) {
                val protoView = protoViewToGameView(view)
                currentController.drawView(protoView)

                if (!initializationFinished)
                    finishInitialization()
            }

            fun finishInitialization() {
                initializationLock.lock()
                initializationFinished = true
                initializationCondition.signal()
                initializationLock.unlock()
            }
        })

        initializationLock.lock()
        while (!initializationFinished) {
            initializationCondition.await()
        }
        initializationLock.unlock()

        if (exception != null)
            throw exception!!
    }

    /**
     * Returns a list of all games available on the server.
     */
    fun getGameList(): List<String> {
        val request = Server.GameListRequest.newBuilder().build()
        return blockingStub.getGameList(request).gamesList
    }

    /**
     * Sends a command made by player [playerName] in game [gameName].
     */
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

    /**
     * Creates a game names [gameName] on server.
     */
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