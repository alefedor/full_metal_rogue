package ru.hse.spb.sd.full_metal_rogue.scene.command

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.scene.handler.SceneHandler

class DirectionCommand(private val receiver: () -> SceneHandler,
                       private val direction: Direction
) : Command {
    override fun execute(): SceneHandler? = receiver().directionAction(direction)
}