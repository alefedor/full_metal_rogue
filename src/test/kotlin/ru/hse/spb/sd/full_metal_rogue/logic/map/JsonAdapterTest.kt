package ru.hse.spb.sd.full_metal_rogue.logic.map

import com.google.gson.JsonSyntaxException
import org.junit.Test
import org.junit.Assert.*
import org.assertj.core.api.Assertions.assertThat
import ru.hse.spb.sd.full_metal_rogue.logic.level.StandardLevelGenerator

class JsonAdapterTest {
    @Test
    fun testMapSerializationDeserialization() {
        val map = StandardLevelGenerator().generateLevel(20, 20)
        val serializedMap = JsonAdapter.gameObjectGson.toJson(map)
        val deserializedMap = deserializeFromString(serializedMap)
        assertThat(map).isEqualToComparingFieldByFieldRecursively(deserializedMap)
    }

    @Test
    fun testDeserializeEmptyJson() {
        val deserializedMap = deserializeFromString("{}")
        assertEquals(0, deserializedMap.height)
        assertEquals(0, deserializedMap.width)
    }

    @Test
    fun testDeserializeEmptyString() {
        assertNull(deserializeFromString(""))
    }

    @Test
    fun testDeserializeBlankString() {
        assertNull(deserializeFromString("  \t "))
    }

    @Test(expected = JsonSyntaxException::class)
    fun testDeserializeNonJsonString() {
        deserializeFromString("some map")
    }

    private fun deserializeFromString(map: String) = JsonAdapter.gameObjectGson.fromJson(map, MutableGameMap::class.java)
}