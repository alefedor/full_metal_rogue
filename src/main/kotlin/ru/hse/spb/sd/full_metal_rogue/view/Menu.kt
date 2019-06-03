package ru.hse.spb.sd.full_metal_rogue.view

/**
 * Represents a navigable menu in the game.
 */
interface Menu<T> {
    /**
     * Returns number of menu items.
     */
    fun size(): Int

    /**
     * Returns item by index.
     */
    operator fun get(index: Int): T

    /**
     * Returns index of selected item,
     */
    fun currentItemIndex(): Int

    /**
     * Returns selected item.
     */
    fun currentItem(): T
}

/**
 * Represents mutable navigable menu in the game.
 */
class MutableMenu<T>(private val items: MutableList<T>) : Menu<T> {
    private var currentItemIndex = 0

    /**
     * @see Menu.size
     */
    override fun size(): Int = items.size

    /**
     * @see Menu.get
     */
    override fun get(index: Int): T = items[index]

    /**
     * @see Menu.currentItemIndex
     */
    override fun currentItemIndex(): Int = currentItemIndex

    /**
     * @see Menu.currentItem
     */
    override fun currentItem(): T = items[currentItemIndex]

    /**
     * Removes selected item.
     */
    fun removeCurrentItem() {
        items.removeAt(currentItemIndex)
        if (currentItemIndex > items.lastIndex && currentItemIndex != 0) {
            currentItemIndex--
        }
    }

    /**
     * Adds an item at the end.
     */
    fun add(item: T) = items.add(item)

    /**
     * If there is next item, changes selected item to it.
     */
    fun toNextItem() {
        if (currentItemIndex < items.lastIndex) {
            currentItemIndex++
        }
    }

    /**
     * If there is previous item, changes selected item to it.
     */
    fun toPreviousItem() {
        if (currentItemIndex > 0) {
            currentItemIndex--
        }
    }

    fun getItemsList() = items
}