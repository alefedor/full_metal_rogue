package ru.hse.spb.sd.full_metal_rogue.view

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.player
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.LevelSceneUIDrawer

/**
 * Representation of game level.
 */
class LevelView(
    val map: GameMap,
    val message: String,
    private val chosenPlayerName: String,
    private val currentPlayerName: String
) : View {
    override fun draw(terminal: AsciiPanel) {
        val drawer = LevelSceneUIDrawer(terminal)
        drawer.clear()
        drawer.drawMap(map, chosenPlayerName)
        drawer.outputMessage(message)
        drawer.outputPlayerState(map.player(chosenPlayerName))
        drawer.outputCurrentTurnHolder(currentPlayerName)
    }
}