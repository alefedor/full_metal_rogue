package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.level.LevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*
import java.awt.Color

class UIDrawer(private val terminal: AsciiPanel) {
    private val mapLeftOffset = terminal.widthInCharacters - LevelGenerator.DEFAULT_MAP_WIDTH
    private val messageOffset = 1

    private val enemiesColors = HashMap<String, Color>()

    fun drawMap(map: GameMap) {
        for (i in 0 until map.height) {
            for (j in 0 until map.width) {
                drawGameObject(map[j, i], j + mapLeftOffset, i + messageOffset)
            }
        }
    }

    fun outputMessage(message: String) {
        terminal.write(message, 0, 0, AsciiPanel.white)
    }

    fun outputPlayerState(player: Player) {
        outputStateCharacteristic("EXP", player.totalExperience, messageOffset)
        outputStateCharacteristic("CUR HP", player.currentHealth, messageOffset + 1)
        outputStateCharacteristic("MAX HP", player.maxHealth, messageOffset + 2)
        outputStateCharacteristic("ATTACK", player.attackPower, messageOffset + 3)
    }

    fun outputStartMessage() {
        outputMessageInCenter("Welcome to Full Metal Rogue.", -3)
        outputMessageInCenter("Press 1 to generate a random level", -2)
        outputMessageInCenter("Press 2 to load a level file from memory", -1)
        outputMessageInCenter("Press Esc to exit", 0)
        outputMessageInCenter("Controls: W-A-S-D for player movement, P to save current map, Esc to exit", +1)
        outputMessageInCenter("Use left and right arrows to navigate between messages " +
                "appearing in the top left corner", +2)
    }

    fun clear() {
        terminal.clear()
    }

    private fun drawGameObject(gameObject: GameObject, x: Int, y: Int) {
        when (gameObject) {
            is Wall -> drawWall(x, y)
            is FreeSpace -> drawFreeSpace(x, y)
            is Player-> drawPlayer(x, y)
            is Enemy -> drawEnemy(gameObject, x, y)
        }
    }

    private fun drawPlayer(x: Int, y: Int) {
        terminal.write(Tile.PLAYER.glyph, x, y, Tile.PLAYER.color)
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

    private fun drawFreeSpace(x: Int, y: Int) {
        terminal.write(Tile.FREE_SPACE.glyph, x, y, Tile.FREE_SPACE.color)
    }

    private fun outputStateCharacteristic(characteristic: String, value: Int, topOffset: Int) {
        terminal.write(characteristic, 0, topOffset, AsciiPanel.white)
        terminal.write("   $value", characteristic.length, topOffset, AsciiPanel.brightGreen)
    }

    private fun outputMessageInCenter(message: String, centerVerticalOffset: Int) {
        terminal.writeCenter(message, terminal.heightInCharacters / 2 + centerVerticalOffset)
    }
}