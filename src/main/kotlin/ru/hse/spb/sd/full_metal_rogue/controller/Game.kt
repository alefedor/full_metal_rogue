package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.SimpleContentGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.TrivialActorGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.MutableGameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.SparseMapInhabitator
import ru.hse.spb.sd.full_metal_rogue.logic.map.playerPosition
import ru.hse.spb.sd.full_metal_rogue.logic.objects.FreeSpace
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.view.DeathView
import ru.hse.spb.sd.full_metal_rogue.view.View
import ru.hse.spb.sd.full_metal_rogue.view.handler.LevelSceneHandler
import ru.hse.spb.sd.full_metal_rogue.view.handler.SceneHandler
import java.util.*

class Game(private val map: MutableGameMap) {
    fun getView(playerName: String): View? {
        if (playerName !in playerList)
            return null

        if (playersByName[playerName]!!.isDead)
            return DeathView(playersByName[playerName]!!)

        return if (handlersStack.isNotEmpty()) handlersStack.peek().view else levelScene.withPlayer(playerName).view
    }

    private var turnPosition: Int = -1
    private val playerList = mutableListOf<String>()
    private val playersByName = mutableMapOf<String, Player>()

    private val levelScene = LevelSceneHandler(map)
    private val handlersStack = Stack<SceneHandler>()

    init {
        for (x in 0 until map.width)
            for (y in 0 until map.height) {
                val gameObject = map.get(x, y)
                if (gameObject is Player) {
                    playerList.add(gameObject.name)
                    playersByName[gameObject.name] = gameObject
                    turnPosition = 0
                }
            }
    }

    fun join(playerName: String): Boolean {
        if (playerName in playerList)
            return false

        if (turnPosition == -1)
            turnPosition = playerList.size

        playerList.add(playerName)

        val player = TrivialActorGenerator(SimpleContentGenerator).generatePlayer(playerName)
        SparseMapInhabitator.inhabitateWithActor(map, player)
        playersByName[playerName] = player

        return true
    }

    fun removePlayer(playerName: String) {
        if (playerName !in playerList)
            return // nothing to do

        val player = playersByName[playerName]!!
        player.takeDamage(player.maxHealth) // automatically kill the player
        map[map.playerPosition(playerName)] = FreeSpace
        if (turnPosition == playerList.indexOf(playerName))
            turnPosition = nextTurnPosition()
    }

    fun makeTurn(playerName: String, command: Command) {
        if (playerName != currentPlayerName())
            return

        val activeHandler = if (handlersStack.empty()) levelScene.withPlayer(playerName) else handlersStack.peek()

        val newHandler = when (command) {
            is BackCommand -> activeHandler.backAction()
            is SelectCommand -> activeHandler.selectAction()
            is DirectionCommand -> activeHandler.directionAction(command.direction)
        }

        when (newHandler) {
            null -> handlersStack.pop()
            activeHandler -> {}
            else -> handlersStack.push(newHandler)
        }

        val movementMade = activeHandler is LevelSceneHandler.LevelSceneHandlerWithPlayer && command is DirectionCommand

        if (movementMade)
            turnPosition = nextTurnPosition()
    }

    private fun currentPlayerName(): String? = if (turnPosition == -1) null else playerList[turnPosition]

    private fun nextTurnPosition(): Int {
        var someOneIsAlive = false
        for (player in playersByName.values)
            if (player.isAlive)
                someOneIsAlive = true

        if (!someOneIsAlive)
            return -1

        do {
            turnPosition++
            if (turnPosition == playerList.size) {
                levelScene.moveEnemies() // end of round
                turnPosition = 0
            }
        } while (playersByName[playerList[turnPosition]]!!.isDead)

        return turnPosition
    }
}