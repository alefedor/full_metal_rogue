package ru.hse.spb.sd.full_metal_rogue.logic.objects

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item

sealed class GameObject

object Wall : GameObject()

object FreeSpace : GameObject()

class Chest(val items: List<Item>) : GameObject()

abstract class Actor(maxHealthValue: Int, attackPowerValue: Int) : GameObject() {
    protected var baseMaxHealth = maxHealthValue
        protected set

    protected var baseAttackPower = attackPowerValue
        protected set

    val maxHealth: Int
        get() = calculateMaxHealth()

    var currentHealth = baseMaxHealth
        protected set

    val attackPower: Int
        get() = calculateAttackPower()

    val isAlive: Boolean
        get() = currentHealth > 0

    val isDead: Boolean
        get() = !isAlive

    fun takeDamage(damage: Int) {
        currentHealth -= damage
    }

    protected open fun calculateMaxHealth() = baseMaxHealth
    protected open fun calculateAttackPower() = baseAttackPower
}