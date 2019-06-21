package ru.hse.spb.sd.full_metal_rogue.grpc

import io.grpc.StatusRuntimeException
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import ru.hse.spb.sd.full_metal_rogue.controller.DirectionCommand
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction

// the reason setUp and tearDown are used instead of the grpcCleanup rule
// is that using the grpcCleanup rule results with an exception
// in which a channel resource can not be released in time at the end of test
class ClientServerInteractionTest {
    companion object {
        private const val host = "localhost"
        private const val port = 10000
    }

    private lateinit var server: FullMetalRogueServer
    private lateinit var client: Client

    @Before
    fun setUp() {
        server = FullMetalRogueServer(port)
        server.start()
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

    // just checks that there are no exceptions
    @Test
    fun testJoinGame() {
        client.createGame("game")
        client.joinGame("game", "player")
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

    // just checks that there are no exceptions
    @Test
    fun testSendCommand() {
        client.createGame("game")
        client.joinGame("game", "player")
        client.sendCommand("game", "player", DirectionCommand(Direction.UP))
    }

    @After
    fun tearDown() {
        server.stop()
    }
}