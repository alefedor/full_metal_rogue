package ru.hse.spb.sd.full_metal_rogue.scene.command

import ru.hse.spb.sd.full_metal_rogue.scene.handler.SceneHandler

class SelectCommand(private val receiver: () -> SceneHandler) : Command {
    override fun execute(): SceneHandler? = receiver().selectAction()
}