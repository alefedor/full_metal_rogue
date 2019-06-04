package ru.hse.spb.sd.full_metal_rogue.logic.map

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.SimpleContentGenerator
import ru.hse.spb.sd.full_metal_rogue.view.ChestView
import ru.hse.spb.sd.full_metal_rogue.view.MutableMenu
import ru.hse.spb.sd.full_metal_rogue.view.View
import ru.hse.spb.sd.full_metal_rogue.view.ViewJsonAdapter

class ViewJsonAdapterTest {
    @Test
    fun testChestView() {
        val items = mutableListOf(
            SimpleContentGenerator.generateItem(), SimpleContentGenerator.generateItem(), SimpleContentGenerator.generateItem()
        )
        val menu = MutableMenu(items)
        testView(ChestView(menu))
    }

    private fun testView(view: View) {
        val json = ViewJsonAdapter.serialize(view)
        assertEquals(view, ViewJsonAdapter.deserialize(json))
    }
}