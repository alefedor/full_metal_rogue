package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.map.DEFAULT_MAP_HEIGHT
import ru.hse.spb.sd.full_metal_rogue.map.DEFAULT_MAP_WIDTH
import ru.hse.spb.sd.full_metal_rogue.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.objects.*
import java.awt.Color

class UIDrawer(private val terminal: AsciiPanel) {
    private val mapLeftOffset = terminal.widthInCharacters - DEFAULT_MAP_WIDTH
    private val leftMapBorder = mapLeftOffset - 1
    private val rightMapBorder = leftMapBorder + DEFAULT_MAP_WIDTH

    private val messageOffset = 1
    private val mapTopOffset = messageOffset + 1
    private val topMapBorder = 1
    private val bottomMapBorder = topMapBorder + DEFAULT_MAP_HEIGHT

    private val enemiesColors = HashMap<String, Color>()

    fun drawMap(map: GameMap) {
        for (i in 0 until map.height) {
            for (j in 0 until map.width) {
                drawGameObject(map[j, i], j + mapLeftOffset, i + mapTopOffset)
            }
        }

        drawBorders()
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
        terminal.writeCenter("Welcome to Full Metal Rogue.", terminal.heightInCharacters / 2 - 3,
            AsciiPanel.brightWhite)
        terminal.writeCenter("Press 1 to generate a random level", terminal.heightInCharacters / 2 - 2)
        terminal.writeCenter("Press 2 to load a level file from memory", terminal.heightInCharacters / 2 - 1)
        terminal.writeCenter("Press Esc to exit", terminal.heightInCharacters / 2)
    }

    fun clear() {
        terminal.clear()
    }


    private fun drawBorders() {
        for (i in leftMapBorder..rightMapBorder) {
            terminal.write(Tile.BOUNDS.glyph, i, topMapBorder, Tile.BOUNDS.color)
            terminal.write(Tile.BOUNDS.glyph, i, bottomMapBorder, Tile.BOUNDS.color)
        }

        for (i in topMapBorder..bottomMapBorder) {
            terminal.write(Tile.BOUNDS.glyph, leftMapBorder, i, Tile.BOUNDS.color)
            terminal.write(Tile.BOUNDS.glyph, rightMapBorder, i, Tile.BOUNDS.color)
        }
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
        terminal.write('@', x, y, AsciiPanel.brightWhite)
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
}