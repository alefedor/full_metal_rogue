package ru.hse.spb.sd.full_metal_rogue.logic.map

import org.junit.Assert.*
import org.junit.Test
import ru.hse.spb.sd.full_metal_rogue.logic.objects.FreeSpace
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Wall

class MutableGameMapTest {
    @Test
    fun testMapCreation() {
        val map = MutableGameMap(5, 10)
        assertEquals(5, map.width)
        assertEquals(10, map.height)

        for (x in 0 until map.width)
            for (y in 0 until map.height)
                assertTrue(map[x, y] is Wall)
    }

    @Test
    fun testSetCell() {
        val map = MutableGameMap(5, 10)
        map.set(4, 9, FreeSpace)
        assertEquals(FreeSpace, map[4, 9])
        assertNotEquals(FreeSpace, map[4, 8])
    }
}