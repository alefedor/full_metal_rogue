package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.scene.Scene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent

abstract class SceneHandler(private val sceneDrawer: SceneDrawer) {
    protected abstract val scene: Scene

    fun repaint() = sceneDrawer.draw(scene)

    abstract fun handleUserInput(key: KeyEvent): SceneHandler?
}