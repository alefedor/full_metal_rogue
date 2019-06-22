package ru.hse.spb.sd.full_metal_rogue.grpc

import io.grpc.StatusRuntimeException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import ru.hse.spb.sd.full_metal_rogue.controller.DirectionCommand
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction

// the reason setUp and tearDown are used instead of the grpcCleanup rule
// is that using the grpcCleanup rule results with an exception
// in which a channel resource can not be released in time at the end of test
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ClientServerInteractionTest {
    companion object {
        private val host = "localhost"
        private val port = FullMetalRogueServer.PORT
    }

    private lateinit var server: FullMetalRogueServer
    private lateinit var client: Client

    @Before
    fun setUp() {
        server = FullMetalRogueServer(port)
        server.start()
        Thread.sleep(5000) // wait for server to initialize
        client = Client(host)
    }

    @Test
    fun testCreateGame() {
        assertTrue(client.getGameList().isEmpty())
        client.createGame("game")
        assertTrue(client.getGameList().contains("game"))
    }

    @Test(expected = StatusRuntimeException::class)
    fun testCreateDuplicateGame() {
        client.createGame("game")
        client.createGame("game")
    }
    
    @Test
    fun testGetGameList() {
        client.createGame("game1")
        client.createGame("game2")
        client.createGame("Game2")
        val gameList = client.getGameList()
        assertTrue(gameList.containsAll(listOf("game1", "game2", "Game2")))
        assertEquals(3, gameList.size)
    }

    @After
    fun tearDown() {
        server.stop()
    }
}