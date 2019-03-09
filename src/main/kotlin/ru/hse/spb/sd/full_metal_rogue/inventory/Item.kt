package ru.hse.spb.sd.full_metal_rogue.inventory

class Item(val type: ItemType, val effects: List<Bonus>)

enum class ItemType {
    Weapon,
    Armor
}

class Bonus(val value: Int, val bonusType: BonusType, val bonusTarget: BonusTarget)

enum class BonusType {
    Multiplier,
    Addend
}

enum class BonusTarget {
    Health,
    Damage
}