package ru.hse.spb.sd.full_metal_rogue.view.command

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.handler.SceneHandler

/**
 * Represents move command.
 */
class DirectionCommand(private val receiver: SceneHandler,
                       private val direction: Direction
) : Command {
    /**
     * @see Command.execute
     */
    override fun execute(): SceneHandler? = receiver.directionAction(direction)
}