package ru.hse.spb.sd.full_metal_rogue.logic.map

import org.junit.Assert.*
import org.junit.Test
import ru.hse.spb.sd.full_metal_rogue.controller.DirectionCommand
import ru.hse.spb.sd.full_metal_rogue.logic.Game
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.SimpleContentGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.TrivialActorGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.objects.FreeSpace

class GameTest {
    fun getSmallMap(): MutableGameMap {
        val map = MutableGameMap(2, 2)
        for (x in 0 until map.width)
            for (y in 0 until map.height)
                map[x, y] = FreeSpace
        return map
    }

    @Test
    fun testCreation() {
        val game = Game(getSmallMap())
        assertEquals(null, game.currentPlayerName())
    }

    @Test
    fun testOnePlayer() {
        val playerName = "Player"
        val map = getSmallMap()
        map[0, 0] = TrivialActorGenerator(SimpleContentGenerator).generatePlayer(playerName)
        val game = Game(map)
        assertEquals(playerName, game.currentPlayerName())
    }

    @Test
    fun testMove() {
        val playerName = "Player"
        val map = getSmallMap()
        map[0, 0] = TrivialActorGenerator(SimpleContentGenerator).generatePlayer(playerName)
        val game = Game(map)
        game.makeTurn(playerName, DirectionCommand(Direction.RIGHT))
        assertEquals(Position(1, 0), map.playerPosition(playerName))
    }

    @Test
    fun testTwoPlayers() {
        val player1 = "Player 1"
        val player2 = "Player 2"
        val map = getSmallMap()
        map[0, 0] = TrivialActorGenerator(SimpleContentGenerator).generatePlayer(player1)
        map[0, 1] = TrivialActorGenerator(SimpleContentGenerator).generatePlayer(player2)
        val game = Game(map)
        game.makeTurn(player1, DirectionCommand(Direction.RIGHT))
        assertEquals(Position(1, 0), map.playerPosition(player1))
        assertEquals(player2, game.currentPlayerName())
        game.makeTurn(player2, DirectionCommand(Direction.RIGHT))
        assertEquals(Position(1, 1), map.playerPosition(player2))
        assertEquals(player1, game.currentPlayerName())
    }

    @Test
    fun testJoin() {
        val player1 = "Player 1"
        val player2 = "Player 2"
        val player3 = "Player 3"
        val player4 = "Player 4"
        val map = getSmallMap()
        map[0, 0] = TrivialActorGenerator(SimpleContentGenerator).generatePlayer(player1)
        map[0, 1] = TrivialActorGenerator(SimpleContentGenerator).generatePlayer(player2)
        map[1, 0] = TrivialActorGenerator(SimpleContentGenerator).generatePlayer(player3)
        val game = Game(map)
        game.join(player4)
        assertEquals(Position(1, 1), map.playerPosition(player4))
    }

    @Test
    fun testRemove() {
        val playerName = "Player"
        val map = getSmallMap()
        map[0, 0] = TrivialActorGenerator(SimpleContentGenerator).generatePlayer(playerName)
        val game = Game(map)
        game.removePlayer(playerName)
        assertTrue(map[0, 0] is FreeSpace)
    }

    @Test
    fun testRemoveNothing() {
        val playerName = "Player"
        val map = getSmallMap()
        val game = Game(map)
        game.removePlayer(playerName)
    }
}