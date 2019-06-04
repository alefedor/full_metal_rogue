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
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.util.concurrent.ConcurrentHashMap

class FullMetalRogueService(private val levelGenerator: LevelGenerator = StandardLevelGenerator()) : FullMetalRogueServerGrpc.FullMetalRogueServerImplBase() {
    private val games = ConcurrentHashMap<String, Game>()
    private val observers = ConcurrentHashMap<String, StreamObserver<Server.View>>()

    /**
     */
    override fun subscribeGame(
        request: Server.SubscribeGameRequest,
        responseObserver: io.grpc.stub.StreamObserver<Server.View>
    ) {
        if (!games.contains(request.gameName)) {
            responseObserver.onError(IllegalArgumentException("No game with such name"))
            return
        }

        observers[request.playerName] = responseObserver
    }

    /**
     */
    override fun sendCommand(
        request: Server.Action,
        responseObserver: io.grpc.stub.StreamObserver<Server.SendCommandResponse>
    ) {
        if (!games.contains(request.gameName)) {
            responseObserver.onError(IllegalArgumentException("No game with such name"))
            return
        }

        val game = games.get(request.gameName)!!

        val updates = mutableListOf<Pair<String, Server.View>>()

        synchronized(game) {
            game.makeTurn(request.playerName, parseCommand(request.command))


            for (entry in observers.entries) {
                val playerName = entry.key
                val observer = entry.value

                val view = game.getView(playerName)//.toJson()
                val update = Server.View.newBuilder().setJson("TODO").build()
                updates.add(playerName to update)
            }
        }

        responseObserver.onCompleted()
    }

    /**
     */
    override fun getGameList(
        request: Server.GameListRequest,
        responseObserver: io.grpc.stub.StreamObserver<Server.GameListResponse>
    ) {
        val response = Server.GameListResponse.newBuilder().addAllGames(games.keys().toList()).build()
        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }

    /**
     */
    override fun createGame(
        request: Server.CreateGameRequest,
        responseObserver: io.grpc.stub.StreamObserver<Server.CreateGameResponse>
    ) {

        synchronized(games) { // synchronized is needed to make creation atomic
            if (games.contains(request.gameName)) {
                responseObserver.onError(IllegalArgumentException("There is already game with such name"))
                return
            }
            games[request.gameName] = Game(levelGenerator.generateLevel())
        }
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
}