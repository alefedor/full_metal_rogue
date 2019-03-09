package ru.hse.spb.sd.full_metal_rogue.inventory

import kotlin.random.Random

interface ItemDropper {
    fun drop(): Item?
}

class SingleItemDropper(private val probability: Double, private val item: Item): ItemDropper {
    override fun drop(): Item? = if (Random.nextDouble() <= probability) item else null
}

class GuarrantedUniformItemDropper(private val items: List<Item>) : ItemDropper {
    init {
        require(items.isNotEmpty())
    }

    override fun drop(): Item? = items[Random.nextInt(items.size)]
}