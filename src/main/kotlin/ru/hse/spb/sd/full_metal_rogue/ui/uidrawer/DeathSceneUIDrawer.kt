package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player

class DeathSceneUIDrawer(terminal: AsciiPanel) : UIDrawer(terminal) {
    /**
     * Outputs a message in case of player death.
     * The message includes the final player state.
     */
    fun outputDeathMessage(player: Player) {
        outputMessageInCenter("You died.", -3)
        outputMessageInCenter("Press Esc to start a new game.", -2)
        outputMessageInCenter("Your final stats:", -1)
        getPlayerStats(player)
            .filter { !listOf(
                CURRENT_HEALTH,
                EXPERIENCE_FOR_NEXT_LEVEL
            ).contains(it.first) }
            .forEachIndexed { index, pair ->
                outputMessageInCenter("${pair.first}: ${pair.second}", index)}
    }
}
