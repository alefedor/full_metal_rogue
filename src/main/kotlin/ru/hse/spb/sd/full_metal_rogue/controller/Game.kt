package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.SimpleContentGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.ActorGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.TrivialActorGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.MutableGameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.SparseMapInhabitator
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.view.View
import ru.hse.spb.sd.full_metal_rogue.view.handler.GameSceneHandler
import ru.hse.spb.sd.full_metal_rogue.view.handler.LevelSceneHandler
import java.lang.IllegalArgumentException
import java.util.*

class Game(private val map: MutableGameMap) {
    val view: View?
        get() = if (handlersStack.isNotEmpty()) handlersStack.peek().view else null

    private val playerList = mutableListOf<String>()

    private val handlersStack = Stack<GameSceneHandler>()

    init {
        handlersStack.push(LevelSceneHandler(map))
        for (x in 0 until map.width)
            for (y in 0 until map.height) {
                val gameObject = map.get(x, y)
                if (gameObject is Player)
                    playerList.add(gameObject.name)
            }
    }

    fun join(playerName: String): Boolean {
        if (playerName in playerList)
            return false
        playerList.add(playerName)
        SparseMapInhabitator.inhabitateWithActor(map, TrivialActorGenerator(SimpleContentGenerator).generatePlayer(playerName))
        return true
    }

    fun removePlayer(playerName: String) {
        TODO()
    }

    fun makeTurn(playerName: String, command: Command) {
        val newHandler = when (command) {
            is BackCommand -> handlersStack.peek().backAction()
            is SelectCommand -> handlersStack.peek().selectAction(playerName)
            is DirectionCommand -> handlersStack.peek().directionAction(playerName, command.direction)
        }

        when (newHandler) {
            null -> handlersStack.pop()
            handlersStack.peek() -> {}
            else -> handlersStack.push(newHandler)
        }
    }
}