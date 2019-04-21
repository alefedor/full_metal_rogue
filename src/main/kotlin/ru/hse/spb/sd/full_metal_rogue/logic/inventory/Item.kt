package ru.hse.spb.sd.full_metal_rogue.logic.inventory

sealed class Item(val name: String, val effects: List<Bonus>)

class Weapon(name: String, effects: List<Bonus>) : Item(name, effects)

class Armor(name: String, effects: List<Bonus>) : Item(name, effects)

class Bonus(val adjective: String, val value: Int, val bonusType: BonusType, val bonusTarget: BonusTarget)

enum class BonusType {
    Multiplier,
    Addend
}

enum class BonusTarget {
    Health,
    Damage
}