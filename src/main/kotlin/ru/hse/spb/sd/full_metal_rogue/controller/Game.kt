package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.logic.level.LevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.StandardLevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.MutableGameMap
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.view.View
import ru.hse.spb.sd.full_metal_rogue.view.handler.GameSceneHandler
import ru.hse.spb.sd.full_metal_rogue.view.handler.LevelSceneHandler
import java.lang.IllegalArgumentException
import java.util.*

class Game(map: MutableGameMap) {
    val view: View
        get() = handlersStack.peek().view

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

    constructor(levelGenerator: LevelGenerator = StandardLevelGenerator()): this(levelGenerator.generateLevel()) {}

    fun join(playerName: String) {
        if (playerName in playerList)
            throw IllegalArgumentException("Player with such name already exists")
    }

    fun removePlayer(playerName: String) {

    }

    fun makeTurn(playerName: String, command: Command) {

    }
}