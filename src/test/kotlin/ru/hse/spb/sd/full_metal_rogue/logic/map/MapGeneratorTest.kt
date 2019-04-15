package ru.hse.spb.sd.full_metal_rogue.logic.map

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import ru.hse.spb.sd.full_metal_rogue.logic.objects.FreeSpace
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Wall

@RunWith(Parameterized::class)
class MapGeneratorTest(val mapGenerator: MapGenerator) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun mapGeneratorImplementations() = listOf<MapGenerator>(CaveMapGenerator)
    }

    @Test
    fun testGenerateMap() {
        val map = mapGenerator.generateMap(5, 10)
        assertEquals(5, map.width)
        assertEquals(10, map.height)
    }

    @Test
    fun testGenerateMapWallBorders() {
        val map = mapGenerator.generateMap(5, 10)

        for (x in listOf(0, map.width - 1))
            for (y in 0 until map.height)
                assertEquals(Wall, map[x, y])

        for (y in listOf(0, map.height - 1))
            for (x in 0 until map.width)
                assertEquals(Wall, map[x, y])

    }

    @Test
    fun testGenerateMapDensityConstraint() {
        val map = mapGenerator.generateMap(5, 10, FreeSpaceDensity.Medium)
        var emptyCells = 0
        val allCells = (map.width - 2) * (map.height - 2) // without borders

        for (x in 0 until map.width)
            for (y in 0 until map.height)
                if (map[x, y] is FreeSpace)
                    emptyCells++

        assertTrue((emptyCells - 1) / allCells.toDouble() <= FreeSpaceDensity.Medium.density)
        assertTrue(FreeSpaceDensity.Medium.density <= (emptyCells + 1) / allCells.toDouble())
    }

    @Test
    fun testGenerateMapConnectivity() {
        val map = mapGenerator.generateMap(5, 10)

        val cellColor = Array(map.width) { Array(map.height) { 0 } }

        var componentsNumber = 0

        for (x in 0 until map.width)
            for (y in 0 until map.height)
                if (map[x, y] is FreeSpace && cellColor[x][y] == 0) {
                    componentsNumber++
                    colorComponent(Position(x, y), componentsNumber, map, cellColor)
                }

        assertEquals(1, componentsNumber)
    }

    private fun colorComponent(position: Position, color: Int, gameMap: GameMap, cellColor: Array<Array<Int>>) {
        cellColor[position.x][position.y] = color

        for (direction in Direction.values()) {
            val nextPosition = position.goToDirection(direction)

            if (!gameMap.inBounds(nextPosition)) continue

            if (gameMap[nextPosition.x, nextPosition.y] is FreeSpace && cellColor[nextPosition.x][nextPosition.y] == 0)
                colorComponent(nextPosition, color, gameMap, cellColor)
        }
    }
}