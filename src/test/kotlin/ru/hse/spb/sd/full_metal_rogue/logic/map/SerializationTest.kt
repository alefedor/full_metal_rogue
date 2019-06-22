package ru.hse.spb.sd.full_metal_rogue.logic.map

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.SimpleContentGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.ActorGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.StandardLevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.level.TrivialActorGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Chest
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.view.ChestView
import ru.hse.spb.sd.full_metal_rogue.view.MutableMenu
import ru.hse.spb.sd.full_metal_rogue.view.View
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class SerializationTest {
    @Test
    fun testChestView() {
        val items = mutableListOf(
            SimpleContentGenerator.generateItem(), SimpleContentGenerator.generateItem(), SimpleContentGenerator.generateItem()
        )
        val menu = MutableMenu(items)
        testDeserializedEquals(ChestView(menu))
    }

    @Test
    fun testMap() {
        val map = StandardLevelGenerator().generateLevel(20, 20)
        testDeserializedEquals(map)
    }

    @Test
    fun testMapWithPlayer() {
        val map = StandardLevelGenerator().generateLevel(20, 20)
        map[0, 0] = TrivialActorGenerator(SimpleContentGenerator).generatePlayer("player")
        testDeserializedEquals(map)
    }

    @Test
    fun testMapWithChest() {
        val map = StandardLevelGenerator().generateLevel(20, 20)
        val items = mutableListOf(
            SimpleContentGenerator.generateItem(), SimpleContentGenerator.generateItem(), SimpleContentGenerator.generateItem()
        )
        map[0, 0] = Chest(items)
        testDeserializedEquals(map)
    }

    private fun testDeserializedEquals(obj: Any) {
        val byteStream = ByteArrayOutputStream()
        val objectStream = ObjectOutputStream(byteStream)
        objectStream.writeObject(obj)
        objectStream.flush()

        val serializedObj = byteStream.toByteArray()
        val deserializedObj = ObjectInputStream(ByteArrayInputStream(serializedObj)).readObject()
        assertThat(obj).isEqualToComparingFieldByFieldRecursively(deserializedObj)
    }
}