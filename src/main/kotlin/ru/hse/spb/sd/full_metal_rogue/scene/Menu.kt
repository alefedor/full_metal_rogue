package ru.hse.spb.sd.full_metal_rogue.scene

interface Menu<T> {
    fun size(): Int

    operator fun get(index: Int): T

    fun currentItemIndex(): Int

    fun currentItem(): T
}

class MutableMenu<T>(private val items: MutableList<T>) : Menu<T> {
    private var currentItemIndex = 0

    override fun size(): Int = items.size

    override fun get(index: Int): T = items[index]

    override fun currentItemIndex(): Int = currentItemIndex

    override fun currentItem(): T = items[currentItemIndex]

    fun removeCurrentItem() = items.removeAt(currentItemIndex)

    fun add(item: T) = items.add(item)

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