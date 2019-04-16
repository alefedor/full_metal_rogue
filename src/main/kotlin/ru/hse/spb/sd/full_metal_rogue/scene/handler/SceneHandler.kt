package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.scene.Scene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent

/**
 * Class that handles user input on a Scene
 */
abstract class SceneHandler(private val sceneDrawer: SceneDrawer) {
    protected abstract val scene: Scene

    /**
     * Redraws scene with updated state
     */
    fun repaint() = sceneDrawer.draw(scene)

    /**
     * Handles user input
     *
     * @return null to return to previous SceneHandler or next SceneHandler
     */
    abstract fun handleUserInput(key: KeyEvent): SceneHandler?
}