package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.objects.*

class UIDrawer(private val terminal: AsciiPanel) {
    //TODO check if terminal.width works as expected
    private val mapLeftOffset = terminal.width - MAP_WIDTH
    private val leftMapBorder = mapLeftOffset - 1
    private val rightMapBorder = leftMapBorder + MAP_WIDTH

    private val messageOffset = 1
    private val mapTopOffset = messageOffset + 1
    private val topMapBorder = 1
    private val bottomMapBorder = topMapBorder + MAP_HEIGHT
    // TODO check if i haven't messed up the dimensions with +-1

    companion object {
        //TODO do these values belong to this class?
        const val MAP_HEIGHT = 36 // use for map generation
        const val MAP_WIDTH = 85 // use for map generation
    }

    fun drawMap(map: GameMap) {
        for (i in 0 until map.height) {
            for (j in 0 until map.width) {
                drawGameObject(map[i, j], i + mapTopOffset, j + mapLeftOffset)
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
            is Enemy -> drawActor(gameObject, x, y)
        }
    }

    private fun drawPlayer(x: Int, y: Int) {
        terminal.write('@', x, y, AsciiPanel.brightWhite)
    }

    private fun drawActor(enemy: Enemy, x: Int, y: Int) {
        terminal.write(enemy.name.first(), x, y, AsciiPanel.brightRed)
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