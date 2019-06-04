package ru.hse.spb.sd.full_metal_rogue.view.handler

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.io.Serializable
import javax.swing.JOptionPane

/**
 * Handles user input on a View.
 */
abstract class SceneHandler : Serializable {
    /**
     * View object to be given to GUI. Is null if we want to reset game mode.
     */
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

    protected fun showErrorMessage(message: String) {
        JOptionPane.showMessageDialog(
            null,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        )
    }

    protected fun showServerUnavailableMessage() {
        showErrorMessage("Server is unavailable.")
    }
}
