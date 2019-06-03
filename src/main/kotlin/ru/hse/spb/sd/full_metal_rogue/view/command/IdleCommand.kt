package ru.hse.spb.sd.full_metal_rogue.view.command

import ru.hse.spb.sd.full_metal_rogue.view.handler.SceneHandler

/**
 * Represents idle command.
 */
class IdleCommand(private val receiver: SceneHandler) : Command {
    /**
     * @see Command.execute
     */
    override fun execute(): SceneHandler? = receiver
}