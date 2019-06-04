package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*
import ru.hse.spb.sd.full_metal_rogue.ui.Tile
import java.awt.Color

class LevelSceneUIDrawer(terminal: AsciiPanel) : UIDrawer(terminal) {
    private val enemiesColors = HashMap<String, Color>()

    /**
     * Draws the game map.
     */
    fun drawMap(map: GameMap, currentPlayerName: String) {
        for (i in 0 until map.height) {
            for (j in 0 until map.width) {
                drawGameObject(map[j, i], j + mapLeftOffset, i + messageOffset, currentPlayerName)
            }
        }
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
        }
    }

    private fun drawPlayer(x: Int, y: Int) {
        terminal.write(Tile.PLAYER.glyph, x, y, Tile.PLAYER.color)
    }

    private fun drawOtherPlayer(x: Int, y: Int) {
        terminal.write(Tile.OTHER_PLAYER.glyph, x, y, Tile.OTHER_PLAYER.color)
    }

    private fun generateRandomBrightColor(): Color {
        val rand = java.util.Random()
        val r = rand.nextFloat()
        val g = rand.nextFloat() / 2f + 0.5f
        val b = rand.nextFloat() / 3f
        return Color(r, g, b).brighter()
    }

    private fun drawEnemy(enemy: Enemy, x: Int, y: Int) {
        val enemyColor = enemiesColors.getOrPut(enemy.name) { generateRandomBrightColor() }
        terminal.write(enemy.name.first(), x, y, enemyColor)
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
}