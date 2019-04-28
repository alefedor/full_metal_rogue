package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.scene.Scene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer

/**
 * Handles user input on a Scene.
 */
abstract class SceneHandler(private val sceneDrawer: SceneDrawer) {
    protected abstract val scene: Scene

    /**
     * Redraws scene with updated state
     */
    fun repaint() = sceneDrawer.draw(scene)

    /**
     * Handles back action
     *
     * @return next SceneHandler or null to return to previous SceneHandler
     */
    open fun backAction(): SceneHandler? = this

    /**
     * Handles select action
     *
     * @return next SceneHandler or null to return to previous SceneHandler
     */
    open fun selectAction(): SceneHandler? = this

    /**
     * Handles direction action
     *
     * @return next SceneHandler or null to return to previous SceneHandler
     */
    open fun directionAction(direction: Direction): SceneHandler? = this
}