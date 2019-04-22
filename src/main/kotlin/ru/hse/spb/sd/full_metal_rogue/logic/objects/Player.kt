package ru.hse.spb.sd.full_metal_rogue.logic.objects

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.*
import kotlin.math.roundToInt

class Player(maxHealth: Int, attackPower: Int) : Actor(maxHealth, attackPower) {
    companion object {
        const val NEEDED_EXPERIENCE_RISE = 1.2
        const val STATS_RISE = 1.11
        const val INITIAL_NEEDED_EXPERIENCE = 10
    }

    val inventory: Inventory = SimpleInventory()

    var nextLevelMark = INITIAL_NEEDED_EXPERIENCE

    var experience = 0
        private set

    var level = 0
        protected set
    
    var weapon: Weapon = Weapon("sharp word", Bonus(0, bonusType = BonusType.Addend), 0.0)
        protected set

    var armor: Armor = Armor("mail of faith", Bonus(0, bonusType = BonusType.Addend))
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
                inventory.add(armor)
                armor = item
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
            level++
            experience -= nextLevelMark
            nextLevelMark = (nextLevelMark * NEEDED_EXPERIENCE_RISE).roundToInt()
            maxHealth = (maxHealth * STATS_RISE).roundToInt()
            attackPower = (attackPower * STATS_RISE).roundToInt()
            currentHealth = maxHealth // instant healing on levelling up
        }

        return levelUp
    }
}