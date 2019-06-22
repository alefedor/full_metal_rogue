package ru.hse.spb.sd.full_metal_rogue.logic.objects

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item
import java.io.Serializable

sealed class GameObject : Serializable

object Wall : GameObject()

object FreeSpace : GameObject()

class Chest(val items: MutableList<Item>) : GameObject()

/**
 * Represents a game object which can move, attack other actors and take damage.
 */
abstract class Actor(
    maxHealthValue: Int,
    attackPowerValue: Int,
    val name: String,
    open val experienceCost: Int
) : GameObject() {
    protected var baseMaxHealth = maxHealthValue

    protected var baseAttackPower = attackPowerValue

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

    abstract fun die(): Chest?

    protected open fun calculateMaxHealth() = baseMaxHealth
    protected open fun calculateAttackPower() = baseAttackPower
}