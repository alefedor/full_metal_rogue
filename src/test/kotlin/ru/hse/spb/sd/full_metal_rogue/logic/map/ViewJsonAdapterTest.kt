package ru.hse.spb.sd.full_metal_rogue.logic.map

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.SimpleContentGenerator
import ru.hse.spb.sd.full_metal_rogue.view.ChestView
import ru.hse.spb.sd.full_metal_rogue.view.MutableMenu
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

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
        val bos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(bos)
        oos.writeObject(view)
        oos.flush()
        val data = bos.toByteArray()
        val bin = ByteArrayInputStream(data)
        val ois = ObjectInputStream(bin)
        val obj = ois.readObject()
        assertThat(view).isEqualToComparingFieldByFieldRecursively(obj as View)
    }
}