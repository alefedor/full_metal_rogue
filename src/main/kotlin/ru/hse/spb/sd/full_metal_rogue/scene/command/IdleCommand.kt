package ru.hse.spb.sd.full_metal_rogue.scene.command

import ru.hse.spb.sd.full_metal_rogue.scene.handler.SceneHandler

/**
 * Represents idle command.
 */
class IdleCommand(private val receiver: () -> SceneHandler) : Command {
    /**
     * @see Command.execute
     */
    override fun execute(): SceneHandler? = receiver()
}