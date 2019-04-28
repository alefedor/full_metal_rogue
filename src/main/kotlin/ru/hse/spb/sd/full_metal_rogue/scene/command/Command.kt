package ru.hse.spb.sd.full_metal_rogue.scene.command

import ru.hse.spb.sd.full_metal_rogue.scene.handler.SceneHandler

/**
 * Represents player's command.
 */
interface Command {
    /**
     * Applies command.
     */
    fun execute(): SceneHandler?
}