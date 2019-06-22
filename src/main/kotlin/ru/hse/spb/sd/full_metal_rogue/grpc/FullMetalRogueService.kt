package ru.hse.spb.sd.full_metal_rogue.grpc

import com.google.protobuf.ByteString
import io.grpc.Status
import io.grpc.stub.StreamObserver
import ru.hse.spb.sd.full_metal_rogue.FullMetalRogueServerGrpc
import ru.hse.spb.sd.full_metal_rogue.Server
import ru.hse.spb.sd.full_metal_rogue.controller.BackCommand
import ru.hse.spb.sd.full_metal_rogue.controller.DirectionCommand
import ru.hse.spb.sd.full_metal_rogue.logic.Game
import ru.hse.spb.sd.full_metal_rogue.controller.SelectCommand
import ru.hse.spb.sd.full_metal_rogue.logic.level.LevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.StandardLevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.DeathView
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.util.concurrent.ConcurrentHashMap

/**
 * Handles client's queries
 */
class FullMetalRogueService(
    private val levelGenerator: LevelGenerator = StandardLevelGenerator()
) : FullMetalRogueServerGrpc.FullMetalRogueServerImplBase() {
    private val sessions = ConcurrentHashMap<String, GameSession>()

    /**
     * Allows the user to join a game and subscribe to a view stream.
     */
    @Synchronized
    override fun subscribeGame(
        request: Server.SubscribeGameRequest,
        responseObserver: StreamObserver<Server.View>
    ) {
        try {
            if (!sessions.containsKey(request.gameName))
                throw IllegalArgumentException("No game with such name")

            val session = sessions[request.gameName]!!
            val observers = session.observers

            synchronized(session.game) {
                val success = session.game.join(request.playerName)

                if (!success)
                    throw java.lang.IllegalArgumentException("The player with such name is already in the game")
            }

            observers[request.playerName] = responseObserver

            sendUpdates(session)
        } catch (e: IllegalArgumentException) {
            responseObserver.onError(
                Status.INVALID_ARGUMENT
                    .withDescription(e.message)
                    .asRuntimeException()
            )
        }
    }

    /**
     * Sends a user command to server in order to make a turn in the game.
     */
    override fun sendCommand(
        request: Server.Action,
        responseObserver: StreamObserver<Server.SendCommandResponse>
    ) {
        if (!sessions.containsKey(request.gameName)) {
            responseObserver.onError(IllegalArgumentException("No game with such name"))
            return
        }

        val session = sessions[request.gameName]!!

        val game = session.game

        synchronized(game) {
            game.makeTurn(request.playerName, parseCommand(request.command))
        }

        responseObserver.onNext(Server.SendCommandResponse.newBuilder().build())
        responseObserver.onCompleted()

        sendUpdates(session)
    }

    /**
     * Returns a list of all available games.
     */
    override fun getGameList(
        request: Server.GameListRequest,
        responseObserver: StreamObserver<Server.GameListResponse>
    ) {
        val response = Server.GameListResponse.newBuilder().addAllGames(sessions.keys().toList()).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    /**
     * Creates a new game with a given name.
     */
    override fun createGame(
        request: Server.CreateGameRequest,
        responseObserver: StreamObserver<Server.CreateGameResponse>
    ) {
        synchronized(sessions) { // synchronized is needed to make creation atomic
            if (sessions.containsKey(request.gameName)) {
                responseObserver.onError(
                    Status.INVALID_ARGUMENT
                        .withDescription("There is already game with such name")
                        .asRuntimeException()
                )
                return
            }
            sessions[request.gameName] = GameSession(Game(levelGenerator.generateLevel()))
        }
        responseObserver.onNext(Server.CreateGameResponse.newBuilder().build())
        responseObserver.onCompleted()
    }

    private fun parseCommand(command: Server.Command) = when(command) {
        Server.Command.SELECT -> SelectCommand
        Server.Command.BACK -> BackCommand
        Server.Command.DOWN -> DirectionCommand(Direction.DOWN)
        Server.Command.UP -> DirectionCommand(Direction.UP)
        Server.Command.LEFT -> DirectionCommand(Direction.LEFT)
        Server.Command.RIGHT -> DirectionCommand(Direction.RIGHT)
        Server.Command.UNRECOGNIZED -> throw IllegalArgumentException("Command is not recognized")
    }

    private fun gameViewToProtoView(view: View?): Server.View {
        val byteArrayStream = ByteArrayOutputStream()
        val objectStream = ObjectOutputStream(byteArrayStream)
        objectStream.writeObject(view)
        objectStream.flush()
        val bytes = byteArrayStream.toByteArray()

        val byteString = ByteString.copyFrom(bytes)
        return Server.View.newBuilder().setBytes(byteString).build()
    }

    @Synchronized
    private fun sendUpdates(session: GameSession) {
        val game = session.game
        val observers = session.observers

        val updates = mutableListOf<Pair<String, View?>>()

        synchronized(game) {
            for (entry in observers.entries) {
                val playerName = entry.key
                val view = game.getView(playerName)
                updates.add(playerName to view)

                val isLeavingTheGame = view == null

                if (isLeavingTheGame)
                    game.autoKillPlayer(playerName)
            }
        }

        for (update in updates) {
            val view = gameViewToProtoView(update.second)
            val observer = observers[update.first]!!
            observer.onNext(view)
        }

        synchronized(game) {
            for (entry in observers.entries) {
                val playerName = entry.key
                val isDead = !game.isAlive(playerName)

                if (isDead) {
                    observers[playerName]?.onCompleted()
                    game.removePlayer(playerName)
                    observers.remove(playerName)
                }
            }
        }
    }

    class GameSession(val game: Game) {
        val observers = ConcurrentHashMap<String, StreamObserver<Server.View>>()
    }
}