package ru.hse.spb.sd.full_metal_rogue.logic.inventory

import ru.hse.spb.sd.full_metal_rogue.logic.objects.Chest
import kotlin.random.Random

interface ContentGenerator {
    fun generateChest(): Chest
    fun generateItem(): Item
    fun generateArmor(): Armor
    fun generateWeapon(): Weapon
}

private data class WeaponCharacteristics(val name: String, val confusionChance: Double)

object SimpleContentGenerator : ContentGenerator {
    private val BASIC_WEAPONS = listOf(
        WeaponCharacteristics("Markov chain", 0.9),
        WeaponCharacteristics("Occam's razor", 0.1)
    )
    private val BASIC_ARMOR_NAMES = listOf("bullyproof vest", "cloak of visibility")


    override fun generateItem() = if (Random.nextBoolean()) generateArmor() else generateWeapon()

    override fun generateChest(): Chest {
        val itemsNumber = Random.nextInt(0, 3)
        return Chest(List(3) { generateItem() })
    }

    override fun generateArmor(): Armor {
        val armorName = BASIC_ARMOR_NAMES.random()
        if (Random.nextBoolean()) {
            return Armor(armorName, Bonus(Random.nextInt(2, 4), BonusType.MULTIPLIER))
        } else {
            return Armor(armorName, Bonus(Random.nextInt(50, 400), BonusType.ADDEND))
        }
    }

    override fun generateWeapon(): Weapon {
        val weaponCharacteristics = BASIC_WEAPONS.random()
        val weaponName = weaponCharacteristics.name
        val confusionChance = weaponCharacteristics.confusionChance
        if (Random.nextBoolean()) {
            return Weapon(weaponName, Bonus(Random.nextInt(2, 4), BonusType.MULTIPLIER), confusionChance)
        } else {
            return Weapon(weaponName, Bonus(Random.nextInt(3, 20), BonusType.ADDEND), confusionChance)
        }
    }
}