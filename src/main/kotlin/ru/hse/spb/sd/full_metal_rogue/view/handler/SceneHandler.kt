package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.View

/**
 * Handles user input on a View.
 */
abstract class SceneHandler {
    abstract val view: View

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

abstract class GameSceneHandler {
    abstract val view: View?

    /**
     * Handles back action
     *
     * @return next SceneHandler or null to return to previous SceneHandler
     */
    open fun backAction(): GameSceneHandler? = this

    /**
     * Handles select action
     *
     * @return next SceneHandler or null to return to previous SceneHandler
     */
    open fun selectAction(playerName: String): GameSceneHandler? = this

    /**
     * Handles direction action
     *
     * @return next SceneHandler or null to return to previous SceneHandler
     */
    open fun directionAction(playerName: String, direction: Direction): GameSceneHandler? = this
}