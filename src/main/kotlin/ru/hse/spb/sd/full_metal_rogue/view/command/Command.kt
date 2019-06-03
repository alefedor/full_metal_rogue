package ru.hse.spb.sd.full_metal_rogue.view.command

import ru.hse.spb.sd.full_metal_rogue.view.handler.SceneHandler

/**
 * Represents player's command.
 */
interface Command {
    /**
     * Applies command.
     */
    fun execute(): SceneHandler?
}