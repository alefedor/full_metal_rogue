package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.level.LevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*
import java.awt.Color

class UIDrawer(private val terminal: AsciiPanel) {
    companion object {
        val CURRENT_HEALTH = "CUR HP"
        val MAX_HEALTH = "MAX HP"
        val ATTACK_POWER = "ATTACK"
        val CURRENT_LEVEL = "LEVEL"
        val CURRENT_EXPERIENCE = "CUR EXP"
        val EXPERIENCE_FOR_NEXT_LEVEL = "NEEDED EXP"
    }

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
        getPlayerStats(player).forEachIndexed { index, pair ->
                outputStateCharacteristic(pair.first, pair.second, messageOffset + index) }
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

    fun outputDeathMessage(player: Player) {
        outputMessageInCenter("You died.", -3)
        outputMessageInCenter("Press Esc to start a new game.", -2)
        outputMessageInCenter("Your final stats:", -1)
        getPlayerStats(player)
            .filter { !listOf(CURRENT_HEALTH, EXPERIENCE_FOR_NEXT_LEVEL).contains(it.first) }
            .forEachIndexed { index, pair ->
            outputMessageInCenter("${pair.first}: ${pair.second}", index)}
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
            is Chest -> drawChest(x, y)
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

    private fun drawChest(x: Int, y: Int) {
        terminal.write(Tile.CHEST.glyph, x, y, Tile.CHEST.color)
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

    private fun getPlayerStats(player: Player): List<Pair<String, Int>> {
        return listOf(
            CURRENT_HEALTH to player.currentHealth,
            MAX_HEALTH to player.maxHealth,
            ATTACK_POWER to player.attackPower,
            CURRENT_LEVEL to player.level,
            CURRENT_EXPERIENCE to player.experience,
            EXPERIENCE_FOR_NEXT_LEVEL to player.nextLevelMark)
    }
}