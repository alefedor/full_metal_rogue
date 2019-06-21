package ru.hse.spb.sd.full_metal_rogue.logic.inventory

import java.io.Serializable

/**
 * An abstraction for an item in the game.
 */
sealed class Item(val name: String) : Serializable

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
class Bonus(val value: Int, val bonusType: BonusType) : Serializable

enum class BonusType {
    MULTIPLIER,
    ADDEND
}

/**
 * Applies an effect of the bonus to the value.
 */
fun Int.apply(bonus: Bonus) = when(bonus.bonusType) {
    BonusType.MULTIPLIER -> this * bonus.value
    BonusType.ADDEND -> this + bonus.value
}

/**
 * Applies reverse effect to an effect of the bonus on the value.
 */
fun Int.unapply(bonus: Bonus) = when(bonus.bonusType) {
    BonusType.MULTIPLIER -> this / bonus.value
    BonusType.ADDEND -> this - bonus.value
}
