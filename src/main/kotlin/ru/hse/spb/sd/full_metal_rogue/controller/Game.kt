package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.logic.level.LevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.StandardLevelGenerator
import ru.hse.spb.sd.full_metal_rogue.view.View
import ru.hse.spb.sd.full_metal_rogue.view.handler.GameSceneHandler
import ru.hse.spb.sd.full_metal_rogue.view.handler.LevelSceneHandler
import java.util.*

class Game(levelGenerator: LevelGenerator = StandardLevelGenerator()) {
    val view: View
        get() = handlersStack.peek().view

    private val playerList = mutableListOf<String>()

    private val handlersStack = Stack<GameSceneHandler>()

    init {
        handlersStack.push(LevelSceneHandler(levelGenerator.generateLevel()))
    }

    fun join(playerName: String) {

    }

    fun removePlayer(playerName: String) {

    }

    fun makeTurn(playerName: String, command: Command) {

    }
}