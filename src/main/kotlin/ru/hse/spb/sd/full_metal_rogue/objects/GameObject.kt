package ru.hse.spb.sd.full_metal_rogue.objects

import ru.hse.spb.sd.full_metal_rogue.inventory.ItemDropper

sealed class GameObject

object Wall : GameObject()

object FreeSpace : GameObject()

class Chest(private val items: List<ItemDropper>) : GameObject()

abstract class Actor(private var maxHealth: Int, private var damage: Int) : GameObject() {
    private var currentHealth = maxHealth

    fun takeDamage(damage: Int) {
        currentHealth -= damage
    }

    fun attackPower(): Int = damage
}