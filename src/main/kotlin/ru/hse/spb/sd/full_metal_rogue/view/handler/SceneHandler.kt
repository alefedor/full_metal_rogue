package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.io.Serializable

/**
 * Handles user input on a View.
 */
abstract class SceneHandler : Serializable {
    abstract val view: View?

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
