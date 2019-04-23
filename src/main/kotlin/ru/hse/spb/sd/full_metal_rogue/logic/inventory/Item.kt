package ru.hse.spb.sd.full_metal_rogue.logic.inventory

sealed class Item(val name: String)

class Weapon(name: String, val effect: Bonus, val confusionChance: Double) : Item(name)

class Armor(name: String, val effect: Bonus) : Item(name)

class Bonus(val value: Int, val bonusType: BonusType)

enum class BonusType {
    MULTIPLIER,
    ADDEND
}

fun apply(value: Int, bonus: Bonus) = when(bonus.bonusType) {
        BonusType.MULTIPLIER -> value * bonus.value
        BonusType.ADDEND -> value + bonus.value
    }

fun unapply(value: Int, bonus: Bonus) = when(bonus.bonusType) {
    BonusType.MULTIPLIER -> value / bonus.value
    BonusType.ADDEND -> value - bonus.value
}
