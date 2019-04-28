package ru.hse.spb.sd.full_metal_rogue.logic.inventory

interface Inventory {
    val size: Int
    fun add(item: Item)
    fun pop(itemId: Int)
    operator fun get(itemId: Int): Item
    fun items(): List<Item>
}

class SimpleInventory : Inventory {
    private val items = mutableListOf<Item>()
    override val size: Int
        get() = items.size

    override fun add(item: Item) {
        items.add(item)
    }

    override fun pop(itemId: Int) {
        items.removeAt(itemId)
    }

    override fun get(itemId: Int) = items[itemId]

    override fun items(): List<Item> = items
}