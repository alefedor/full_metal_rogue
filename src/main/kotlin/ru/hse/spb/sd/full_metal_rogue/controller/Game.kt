package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.SimpleContentGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.ActorGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.TrivialActorGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.MutableGameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.SparseMapInhabitator
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.view.View
import ru.hse.spb.sd.full_metal_rogue.view.handler.LevelSceneHandler
import ru.hse.spb.sd.full_metal_rogue.view.handler.SceneHandler
import java.util.*

class Game(private val map: MutableGameMap) {
    val view: View?
        get() = if (handlersStack.isNotEmpty()) handlersStack.peek().view else levelScene.view

    private val playerList = mutableListOf<String>()

    private val levelScene = LevelSceneHandler(map)
    private val handlersStack = Stack<SceneHandler>()

    init {
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
    }
}