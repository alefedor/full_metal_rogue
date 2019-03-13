package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.scene.DeathScene
import ru.hse.spb.sd.full_metal_rogue.scene.LevelScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent

class DeathSceneHandler(sceneDrawer: SceneDrawer) : SceneHandler(sceneDrawer) {
    override val scene: DeathScene
        get() = TODO("not implemented")

    override fun handleUserInput(key: KeyEvent): SceneHandler? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}