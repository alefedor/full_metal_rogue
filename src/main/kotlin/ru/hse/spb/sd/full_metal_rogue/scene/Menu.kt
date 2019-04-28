package ru.hse.spb.sd.full_metal_rogue.scene

interface Menu<T> {
    fun size(): Int

    operator fun get(index: Int): T

    fun currentItemIndex(): Int
}

class MutableMenu<T>(val items: List<T>) : Menu<T> {
    private var currentItemIndex = 0

    override fun size(): Int = items.size

    override fun get(index: Int): T = items[index]

    override fun currentItemIndex(): Int = currentItemIndex

    fun toNextItem() {
        if (currentItemIndex < items.lastIndex) {
            currentItemIndex++
        }
    }

    fun toPreviousItem() {
        if (currentItemIndex > 0) {
            currentItemIndex--
        }
    }
}