package ru.hse.spb.sd.full_metal_rogue.grpc

import io.grpc.stub.StreamObserver
import ru.hse.spb.sd.full_metal_rogue.FullMetalRogueServerGrpc
import ru.hse.spb.sd.full_metal_rogue.Server
import ru.hse.spb.sd.full_metal_rogue.controller.BackCommand
import ru.hse.spb.sd.full_metal_rogue.controller.DirectionCommand
import ru.hse.spb.sd.full_metal_rogue.controller.Game
import ru.hse.spb.sd.full_metal_rogue.controller.SelectCommand
import ru.hse.spb.sd.full_metal_rogue.logic.level.LevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.StandardLevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.DeathView
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.util.concurrent.ConcurrentHashMap

class FullMetalRogueService(private val levelGenerator: LevelGenerator = StandardLevelGenerator()) : FullMetalRogueServerGrpc.FullMetalRogueServerImplBase() {
    private val sessions = ConcurrentHashMap<String, GameSession>()

    /**
     */
    override fun subscribeGame(
        request: Server.SubscribeGameRequest,
        responseObserver: StreamObserver<Server.View>
    ) {
        if (!sessions.containsKey(request.gameName)) {
            responseObserver.onError(IllegalArgumentException("No game with such name"))
            return
        }

        val session = sessions[request.gameName]!!
        val observers = session.observers

        observers[request.playerName] = responseObserver
    }

    /**
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
        val observers = session.observers

        val updates = mutableListOf<Pair<String, View?>>()

        synchronized(game) {
            game.makeTurn(request.playerName, parseCommand(request.command))


            for (entry in observers.entries) {
                val playerName = entry.key
                val view = game.getView(playerName)
                updates.add(playerName to view)
            }
        }

        responseObserver.onNext(Server.SendCommandResponse.newBuilder().build())
        responseObserver.onCompleted()

        for (update in updates) {
            val view = Server.View.newBuilder().setJson("").build()
            val observer = observers[update.first]!!
            observer.onNext(view)
            /*if (update.second is DeathView)
                observer.onCompleted()*/
        }
    }

    /**
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
     */
    override fun createGame(
        request: Server.CreateGameRequest,
        responseObserver: StreamObserver<Server.CreateGameResponse>
    ) {

        synchronized(sessions) { // synchronized is needed to make creation atomic
            if (sessions.containsKey(request.gameName)) {
                responseObserver.onError(IllegalArgumentException("There is already game with such name"))
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

    class GameSession(val game: Game) {
        val observers = ConcurrentHashMap<String, StreamObserver<Server.View>>()
    }
}