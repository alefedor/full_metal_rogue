package ru.hse.spb.sd.full_metal_rogue.logic.objects

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.*
import kotlin.math.roundToInt

/**
 * Represents a game player. A player has health, attack, and name attributes, inventory and equipped items.
 */
class Player(maxHealth: Int, attackPower: Int, name: String) : Actor(maxHealth, attackPower, name, 0) {
    companion object {
        const val NEEDED_EXPERIENCE_RISE = 1.2
        const val STATS_RISE = 1.11
        const val INITIAL_NEEDED_EXPERIENCE = 10
    }

    override val experienceCost: Int
        get() = experience // all experience of killed player

    val inventory: Inventory = SimpleInventory()

    var nextLevelMark = INITIAL_NEEDED_EXPERIENCE

    var experience = 0
        private set

    var level = 0
        protected set

    var weapon: Weapon = Weapon("sharp word", Bonus(0, bonusType = BonusType.ADDEND), 0.0)
        protected set

    var armor: Armor = Armor("mail of faith", Bonus(0, bonusType = BonusType.ADDEND))
        protected  set

    fun equip(itemId: Int) {
        val item = inventory[itemId]
        inventory.pop(itemId)

        when(item) {
            is Weapon -> {
                inventory.add(weapon)
                weapon = item
            }
            is Armor -> {
                currentHealth = unapply(currentHealth, armor.effect)
                inventory.add(armor)
                armor = item
                currentHealth = apply(currentHealth, armor.effect)
            }
        }
    }

    fun earnExperience(experience: Int): Boolean {
        this.experience += experience
        return checkLevelUp()
    }

    private fun checkLevelUp(): Boolean {
        var levelUp = false

        while (experience >= nextLevelMark) {
            levelUp = true
            levelUp()
        }

        return levelUp
    }

    private fun levelUp() {
        level++
        nextLevelMark += (nextLevelMark * NEEDED_EXPERIENCE_RISE).roundToInt()
        baseMaxHealth = (baseMaxHealth * STATS_RISE).roundToInt()
        baseAttackPower = (baseAttackPower * STATS_RISE).roundToInt()
        currentHealth = maxHealth // instant healing on levelling up
    }

    override fun die(): Chest? {
        val chest = Chest(inventory.items().toMutableList())
        chest.items.add(weapon)
        chest.items.add(armor)
        return chest
    }

    override fun calculateMaxHealth() = apply(baseMaxHealth, armor.effect)
    override fun calculateAttackPower() = apply(baseAttackPower, weapon.effect)
}