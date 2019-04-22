package ru.hse.spb.sd.full_metal_rogue.logic.inventory

sealed class Item(val name: String)

class Weapon(name: String, val effect: Bonus, val shockChance: Double) : Item(name)

class Armor(name: String, val effect: Bonus) : Item(name)

class Bonus(val value: Int, val bonusType: BonusType)

enum class BonusType {
    Multiplier,
    Addend
}