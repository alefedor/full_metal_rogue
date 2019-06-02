package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.*
import ru.hse.spb.sd.full_metal_rogue.logic.level.LevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*
import ru.hse.spb.sd.full_metal_rogue.scene.Menu
import java.awt.Color

/**
 * Handles writing to terminal.
 */
abstract class UIDrawer(protected val terminal: AsciiPanel) {
    companion object {
        const val CURRENT_HEALTH = "CUR HP"
        const val MAX_HEALTH = "MAX HP"
        const val ATTACK_POWER = "ATTACK"
        const val CURRENT_LEVEL = "LEVEL"
        const val CURRENT_EXPERIENCE = "CUR EXP"
        const val EXPERIENCE_FOR_NEXT_LEVEL = "NEEDED EXP"
    }

    protected val mapLeftOffset = terminal.widthInCharacters - LevelGenerator.DEFAULT_MAP_WIDTH
    protected val messageOffset = 1
    protected val leftOffset = 4


    /**
     * Outputs a message in the top left corner of the terminal.
     */
    fun outputMessage(message: String) {
        terminal.write(message, 0, 0, AsciiPanel.white)
    }

    /**
     * Outputs player state in the left panel.
     * The state consists of a characteristic and its value.
     */
    fun outputPlayerState(player: Player) {
        getPlayerStats(player).forEachIndexed { index, pair ->
                outputStateCharacteristic(pair.first, pair.second, messageOffset + index) }
    }

    /**
     * Outputs a header message in the top left part of the terminal.
     */
    fun outputHeader(header: String, offset: Int = leftOffset) {
        terminal.write(header, offset, 0, AsciiPanel.brightYellow)
    }

    /**
     * Clears the terminal.
     */
    fun clear() {
        terminal.clear()
    }

    protected fun outputMessageInCenter(message: String, centerVerticalOffset: Int, color: Color = AsciiPanel.white) {
        terminal.writeCenter(message, terminal.heightInCharacters / 2 + centerVerticalOffset, color)
    }

    protected fun getPlayerStats(player: Player): List<Pair<String, Int>> {
        return listOf(
            CURRENT_HEALTH to player.currentHealth,
            MAX_HEALTH to player.maxHealth,
            ATTACK_POWER to player.attackPower,
            CURRENT_LEVEL to player.level,
            CURRENT_EXPERIENCE to player.experience,
            EXPERIENCE_FOR_NEXT_LEVEL to player.nextLevelMark)
    }

    protected fun outputItem(item: Item,
                           y: Int,
                           leftOffset: Int,
                           itemTypePosition: Int,
                           bonusValuePosition: Int,
                           confusionChancePosition: Int,
                           isCurrentItem: Boolean = false) {
        val itemColor = if (isCurrentItem) AsciiPanel.green else AsciiPanel.white
        terminal.write(item.name, leftOffset, y, itemColor)
        when (item) {
            is Weapon -> {
                terminal.write('W', itemTypePosition, y, itemColor)
                outputBonus(item.effect, bonusValuePosition, y, itemColor)
                terminal.write(item.confusionChance.toString(), confusionChancePosition, y, itemColor)
            }
            is Armor -> {
                terminal.write('A', itemTypePosition, y, itemColor)
                outputBonus(item.effect, bonusValuePosition, y, itemColor)
            }
        }
    }

    protected fun outputMenuItems(itemMenu: Menu<Item>,
                                  offset: Int = leftOffset,
                                  itemTypePosition: Int,
                                  bonusValuePosition: Int,
                                  confusionChancePosition: Int) {
        var y = 3
        for (i in 0 until itemMenu.size()) {
            if (i == itemMenu.currentItemIndex()) {
                outputItem(itemMenu[i], y++, offset, itemTypePosition, bonusValuePosition, confusionChancePosition, true)
            } else {
                outputItem(itemMenu[i], y++, offset, itemTypePosition, bonusValuePosition, confusionChancePosition)
            }
        }
    }

    private fun outputStateCharacteristic(characteristic: String, value: Int, topOffset: Int) {
        terminal.write(characteristic, 0, topOffset, AsciiPanel.white)
        val valueStartPosition = mapLeftOffset - 1 - value.toString().length
        terminal.write("$value", valueStartPosition, topOffset, AsciiPanel.brightGreen)
    }

    private fun outputBonus(effect: Bonus, x: Int, y: Int, itemColor: Color = AsciiPanel.white) {
        val bonusSign = when (effect.bonusType) {
            BonusType.MULTIPLIER -> "*"
            BonusType.ADDEND -> "+"
        }
        val bonusValue = "$bonusSign${effect.value}"
        terminal.write(bonusValue, x, y, itemColor)
    }
}