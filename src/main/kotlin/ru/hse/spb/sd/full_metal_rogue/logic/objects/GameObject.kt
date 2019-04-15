package ru.hse.spb.sd.full_metal_rogue.logic.objects

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.ItemDropper

sealed class GameObject

object Wall : GameObject()

object FreeSpace : GameObject()

class Chest(private val items: List<ItemDropper>) : GameObject()

abstract class Actor(maxHealthValue: Int, attackPowerValue: Int) : GameObject() {
    var maxHealth = maxHealthValue
        protected set

    var currentHealth = maxHealth
        protected set

    var attackPower = attackPowerValue
        protected set

    val isAlive: Boolean
        get() = currentHealth > 0

    val isDead: Boolean
        get() = !isAlive

    fun takeDamage(damage: Int) {
        currentHealth -= damage
    }
}