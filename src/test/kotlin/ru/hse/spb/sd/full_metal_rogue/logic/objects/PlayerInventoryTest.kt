package ru.hse.spb.sd.full_metal_rogue.logic.objects

import org.junit.Test

import org.junit.Assert.*
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.*

class PlayerInventoryTest {
    @Test
    fun testEquipWeapon() {
        val player = Player(2, 2, "player")
        val weapon = Weapon("weapon", Bonus(1, BonusType.ADDEND), 0.0)
        val armor = player.armor

        equip(player, weapon)
        assertPlayerEquipment(player, armor, weapon)
    }

    @Test
    fun testEquipArmor() {
        val player = Player(2, 2, "player")
        val armor = Armor("armor", Bonus(1, BonusType.ADDEND))
        val weapon = player.weapon

        equip(player, armor)
        assertPlayerEquipment(player, armor, weapon)
    }

    @Test
    fun testReequipWeapon() {
        val player = Player(2, 2, "player")
        val weapon = Weapon("weapon", Bonus(1, BonusType.ADDEND), 0.0)
        val newWeapon = Weapon("new weapon", Bonus(2, BonusType.MULTIPLIER), 0.2)
        val armor = player.armor

        equip(player, weapon)
        equip(player, newWeapon)
        assertPlayerEquipment(player, armor, newWeapon)
    }

    @Test
    fun testReequipArmor() {
        val player = Player(2, 2, "player")
        val armor = Armor("armor", Bonus(1, BonusType.ADDEND))
        val newArmor = Armor("new armor", Bonus(2, BonusType.MULTIPLIER))
        val weapon = player.weapon

        equip(player, armor)
        equip(player, newArmor)
        assertPlayerEquipment(player, newArmor, weapon)
    }

    @Test
    fun testMultiplierArmorBonus() {
        val player = Player(2, 2, "player")
        val armor = Armor("armor", Bonus(3, BonusType.MULTIPLIER))

        equip(player, armor)
        assertPlayerStats(player, 6, 2)
    }

    @Test
    fun testAddendArmorBonus() {
        val player = Player(2, 2, "player")
        val armor = Armor("armor", Bonus(3, BonusType.ADDEND))

        equip(player, armor)
        assertPlayerStats(player, 5, 2)
    }

    @Test
    fun testMultiplierWeaponBonus() {
        val player = Player(2, 2, "player")
        val weapon = Weapon("weapon", Bonus(5, BonusType.MULTIPLIER), 0.1)

        equip(player, weapon)
        assertPlayerStats(player, 2, 10)
    }

    @Test
    fun testAddendWeaponBonus() {
        val player = Player(2, 2, "player")
        val weapon = Weapon("weapon", Bonus(5, BonusType.ADDEND), 0.1)

        equip(player, weapon)
        assertPlayerStats(player, 2, 7)
    }

    private fun equip(player: Player, item: Item) {
        player.inventory.add(item)
        player.equip(player.inventory.size - 1)
    }

    private fun assertPlayerEquipment(player: Player, armor: Item, weapon: Item) {
        assertEquals(armor, player.armor)
        assertEquals(weapon, player.weapon)
    }

    private fun assertPlayerStats(player: Player, health: Int, damage: Int) {
        assertEquals(health, player.maxHealth)
        assertEquals(damage, player.attackPower)
    }
}