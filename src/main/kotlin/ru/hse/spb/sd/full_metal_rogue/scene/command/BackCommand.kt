package ru.hse.spb.sd.full_metal_rogue.scene.command

import ru.hse.spb.sd.full_metal_rogue.scene.handler.SceneHandler

/**
 * Represents back command.
 */
class BackCommand(private val receiver: () -> SceneHandler): Command {
    /**
     * @see Command.execute
     */
    override fun execute(): SceneHandler? = receiver().backAction()
}