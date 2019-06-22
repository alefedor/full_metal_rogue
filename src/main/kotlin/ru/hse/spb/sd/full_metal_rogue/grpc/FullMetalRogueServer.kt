package ru.hse.spb.sd.full_metal_rogue.grpc

import io.grpc.Server
import io.grpc.ServerBuilder
import java.util.logging.Logger

/**
 * A sample gRPC server that serves the FullMetalRogue service.
 */
class FullMetalRogueServer(private val port: Int) {
    private val server: Server = ServerBuilder.forPort(port).addService(FullMetalRogueService())
        .build()

    /**
     * Start serving requests.
     */
    fun start() {
        server.start()
        logger.info("Server started, listening on $port")
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down")
                this@FullMetalRogueServer.stop()
                System.err.println("*** server shut down")
            }
        })
    }

    /**
     * Stop serving requests and shutdown resources.f
     */
    fun stop() {
        server.shutdown()
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private fun blockUntilShutdown() {
        server.awaitTermination()
    }

    companion object {
        const val PORT = 10000
        private val logger = Logger.getLogger(FullMetalRogueServer::class.java.name)

        @JvmStatic
        fun main(args: Array<String>) {
            val server = FullMetalRogueServer(PORT)
            server.start()
            server.blockUntilShutdown()
        }
    }
}