package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.ChestScene
import ru.hse.spb.sd.full_metal_rogue.DeathScene
import ru.hse.spb.sd.full_metal_rogue.InventoryScene
import ru.hse.spb.sd.full_metal_rogue.LevelScene
import ru.hse.spb.sd.full_metal_rogue.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.objects.*
import javax.swing.JFrame


class SceneDrawer : JFrame() {
    companion object {
        private const val MAP_HEIGHT = 36 // use for map generation
        private const val MAP_WIDTH = 85 // use for map generation

        private const val WINDOW_HEIGHT = 40
        private const val WINDOW_WIDTH = 100
        private const val MAP_LEFT_OFFSET = WINDOW_WIDTH - MAP_WIDTH
        private const val MESSAGE_OFFSET = 1
        private const val MAP_TOP_OFFSET = MESSAGE_OFFSET + 1
        private const val TOP_MAP_BORDER = 1
        private const val BOTTOM_MAP_BORDER = TOP_MAP_BORDER + MAP_HEIGHT
        private const val LEFT_MAP_BORDER = MAP_LEFT_OFFSET - 1
        private const val RIGHT_MAP_BORDER = LEFT_MAP_BORDER + MAP_WIDTH
        // TODO check if i haven't messed up the dimensions with +-1
    }

    private val terminal = AsciiPanel(WINDOW_WIDTH, WINDOW_HEIGHT)

    init {
        add(terminal)
        pack()
    }

    private fun drawMap(map: GameMap) {
        for (i in 0 until map.height) {
            for (j in 0 until map.width) {
                drawGameObject(map[i, j], i + MAP_TOP_OFFSET, j + MAP_LEFT_OFFSET)
            }
        }

        drawBorders()
    }

    private fun drawBorders() {
        for (i in LEFT_MAP_BORDER..RIGHT_MAP_BORDER) {
            terminal.write(Tile.BOUNDS.glyph, i, TOP_MAP_BORDER, Tile.BOUNDS.color)
            terminal.write(Tile.BOUNDS.glyph, i, BOTTOM_MAP_BORDER, Tile.BOUNDS.color)
        }

        for (i in TOP_MAP_BORDER..BOTTOM_MAP_BORDER) {
            terminal.write(Tile.BOUNDS.glyph, LEFT_MAP_BORDER, i, Tile.BOUNDS.color)
            terminal.write(Tile.BOUNDS.glyph, RIGHT_MAP_BORDER, i, Tile.BOUNDS.color)
        }
    }

    private fun drawGameObject(gameObject: GameObject, x: Int, y: Int) {
        when (gameObject) {
            is Wall -> drawWall(x, y)
            is FreeSpace -> drawFreeSpace(x, y)
            is Actor -> drawActor(gameObject, x, y)
        }
    }

    private fun drawActor(actor: Actor, x: Int, y: Int) {
        when (actor) {
            // TODO the reason why it's not in the Tile enum... why, indeed...
            is Player -> terminal.write('@', x, y, AsciiPanel.brightWhite)
            // TODO different colors and glyphs for different kinds of mobs
            // how can i gain access to mob names?
            is Enemy -> terminal.write(actor.name.first(), x, y, AsciiPanel.brightRed)
        }
    }

    private fun drawWall(x: Int, y: Int) {
        terminal.write(Tile.WALL.glyph, x, y, Tile.WALL.color)
    }

    private fun drawFreeSpace(x: Int, y: Int) {
        terminal.write(Tile.FREE_SPACE.glyph, x, y, Tile.FREE_SPACE.color)
    }

    private fun outputMessage(message: String) {
        terminal.write(message, 0, 0, AsciiPanel.white)
    }

    private fun outputStateCharacteristic(characteristic: String, value: Int, topOffset: Int) {
        terminal.write(characteristic, 0, topOffset, AsciiPanel.white)
        terminal.write("   $value", characteristic.length, topOffset, AsciiPanel.brightGreen)
    }

    private fun outputPlayerState(player: Player) {
        outputStateCharacteristic("EXP", player.totalExperience, MESSAGE_OFFSET)
        outputStateCharacteristic("CUR HP", player.currentHealth, MESSAGE_OFFSET + 1)
        outputStateCharacteristic("MAX HP", player.maxHealth, MESSAGE_OFFSET + 2)
        outputStateCharacteristic("ATTACK", player.attackPower, MESSAGE_OFFSET + 3)
    }

    fun draw(scene: LevelScene) {
        drawMap(scene.level.map)
        outputMessage(scene.message)
        outputPlayerState(scene.player)
    }

    fun draw(scene: InventoryScene) {}

    fun draw(scene: ChestScene) {}

    fun draw(scene: DeathScene) {}

}