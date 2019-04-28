package ru.hse.spb.sd.full_metal_rogue.logic.inventory

/**
 * An abstraction for an item in the game.
 */
sealed class Item(val name: String)

/**
 * Weapon class item.
 */
class Weapon(name: String, val effect: Bonus, val confusionChance: Double) : Item(name)

/**
 * Armor class item.
 */
class Armor(name: String, val effect: Bonus) : Item(name)

/**
 * An effect on characteristics.
 * Either characteristics are multiplied by a value or a value is added to characteristics.
 */
class Bonus(val value: Int, val bonusType: BonusType)

enum class BonusType {
    MULTIPLIER,
    ADDEND
}

/**
 * Applies an effect of the bonus to the value.
 */
fun apply(value: Int, bonus: Bonus) = when(bonus.bonusType) {
        BonusType.MULTIPLIER -> value * bonus.value
        BonusType.ADDEND -> value + bonus.value
    }

/**
 * Applies reverse effect to an effect of the bonus on the value.
 */
fun unapply(value: Int, bonus: Bonus) = when(bonus.bonusType) {
    BonusType.MULTIPLIER -> value / bonus.value
    BonusType.ADDEND -> value - bonus.value
}
