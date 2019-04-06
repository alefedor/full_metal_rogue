package ru.hse.spb.sd.full_metal_rogue.map

import org.junit.Assert.*
import org.junit.Test

class NavigationKtTest {
    @Test
    fun testDistanceTo() {
        assertEquals(0, Position(0, 0).distanceTo(Position(0, 0)))
        assertEquals(1, Position(0, 1).distanceTo(Position(0, 2)))
        assertEquals(3, Position(0, 1).distanceTo(Position(2, 2)))
        assertEquals(3, Position(2, 2).distanceTo(Position(0, 1)))
    }
}