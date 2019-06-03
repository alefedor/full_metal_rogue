package ru.hse.spb.sd.full_metal_rogue.grpc

import ru.hse.spb.sd.full_metal_rogue.FullMetalRogueServerGrpc

class FullMetalRogueService : FullMetalRogueServerGrpc.FullMetalRogueServerImplBase() {
    override fun joinGame(
        responseObserver: io.grpc.stub.StreamObserver<ru.hse.spb.sd.full_metal_rogue.Server.View>
    ): io.grpc.stub.StreamObserver<ru.hse.spb.sd.full_metal_rogue.Server.Action> {
        TODO()
    }

    /**
     */
    override fun getGameList(
        request: ru.hse.spb.sd.full_metal_rogue.Server.GameListRequest,
        responseObserver: io.grpc.stub.StreamObserver<ru.hse.spb.sd.full_metal_rogue.Server.GameListResponse>
    ) {
        TODO()
    }

    /**
     */
    override fun createGame(
        request: ru.hse.spb.sd.full_metal_rogue.Server.CreateGameRequest,
        responseObserver: io.grpc.stub.StreamObserver<ru.hse.spb.sd.full_metal_rogue.Server.CreateGameResponse>
    ) {
        TODO()
    }
}