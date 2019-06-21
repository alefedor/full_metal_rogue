package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.ascii

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*
import ru.hse.spb.sd.full_metal_rogue.ui.Tile
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.LevelSceneUIDrawer
import java.awt.Color
import java.lang.IllegalStateException

/**
 * Handles writing to terminal for LevelView.
 */
class LevelSceneAsciiUIDrawer(terminal: AsciiPanel) : AsciiUIDrawer(terminal), LevelSceneUIDrawer {
    private val enemiesColors = HashMap<String, Color>()

    /**
     * Draws the game map.
     */
    override fun drawMap(map: GameMap, currentPlayerName: String) {
        for (i in 0 until map.height) {
            for (j in 0 until map.width) {
                drawGameObject(map[j, i], j + mapLeftOffset, i + messageOffset, currentPlayerName)
            }
        }
    }

    /**
     * Outputs player state in the left panel.
     * The state consists of a characteristic and its value.
     */
    override fun outputPlayerState(player: Player) {
        getPlayerStats(player).forEachIndexed { index, pair ->
            outputStateCharacteristic(pair.first, pair.second, messageOffset + index)
        }
    }

    /**
     * Outputs a message in the top left corner of the terminal.
     */
    override fun outputMessage(message: String) {
        terminal.write(message, 0, 0, AsciiPanel.white)
    }

    /**
     * Outputs which player's turn. It currently is in the left panel.
     */
    override fun outputCurrentTurnHolder(currentPlayerName: String) {
        terminal.write("Current turn:", 0, terminal.heightInCharacters- 3)
        terminal.write(currentPlayerName, 0, terminal.heightInCharacters - 2)
    }

    private fun drawGameObject(gameObject: GameObject, x: Int, y: Int, currentPlayerName: String) {
        when (gameObject) {
            is Wall -> drawWall(x, y)
            is FreeSpace -> drawFreeSpace(x, y)
            is Player -> {
                when (gameObject.name) {
                    currentPlayerName -> drawPlayer(x, y)
                    else -> drawOtherPlayer(x, y)
                }
            }
            is Enemy -> drawEnemy(gameObject, x, y)
            is Chest -> drawChest(x, y)
            else -> throw IllegalStateException("Unrecognized object")
        }
    }

    private fun drawPlayer(x: Int, y: Int) {
        terminal.write(Tile.PLAYER.glyph, x, y, Tile.PLAYER.color)
    }

    private fun drawOtherPlayer(x: Int, y: Int) {
        terminal.write(Tile.OTHER_PLAYER.glyph, x, y, Tile.OTHER_PLAYER.color)
    }

    private fun drawEnemy(enemy: Enemy, x: Int, y: Int) {
        terminal.write(enemy.name.first(), x, y, enemy.color)
    }

    private fun drawWall(x: Int, y: Int) {
        terminal.write(Tile.WALL.glyph, x, y, Tile.WALL.color)
    }

    private fun drawChest(x: Int, y: Int) {
        terminal.write(Tile.CHEST.glyph, x, y, Tile.CHEST.color)
    }

    private fun drawFreeSpace(x: Int, y: Int) {
        terminal.write(Tile.FREE_SPACE.glyph, x, y, Tile.FREE_SPACE.color)
    }

    private fun outputStateCharacteristic(characteristic: String, value: Int, topOffset: Int) {
        terminal.write(characteristic, 0, topOffset, AsciiPanel.white)
        val valueStartPosition = mapLeftOffset - 1 - value.toString().length
        terminal.write("$value", valueStartPosition, topOffset, AsciiPanel.brightGreen)
    }
}