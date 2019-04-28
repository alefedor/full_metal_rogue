package ru.hse.spb.sd.full_metal_rogue.logic.map

import com.google.gson.*
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.Behaviour
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Inventory
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item
import ru.hse.spb.sd.full_metal_rogue.logic.objects.GameObject
import java.io.IOException
import java.lang.reflect.Type
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.JOptionPane

/**
 * Handles save/load of game map to a save file.
 */
object FileMapLoader {
    private const val SAVE_NAME = "save.txt"

    /**
     * Loads map from the save file.
     */
    fun loadMap(): MutableGameMap? {
        val file = Paths.get(SAVE_NAME).toFile()
        return try {
            JsonAdapter.gameObjectGson.fromJson(file.readText(), MutableGameMap::class.java)
        } catch (exception: Exception) {
            showErrorDialog("Unable to load previous save.")
            null
        }
    }

    /**
     * Deletes save file.
     */
    fun deleteMap() {
        Files.deleteIfExists(Paths.get(SAVE_NAME))
    }

    /**
     * Writes map to the save file.
     */
    fun saveMap(map: GameMap): Boolean {
        val serializedMap = JsonAdapter.gameObjectGson.toJson(map)
        val file = Paths.get(SAVE_NAME).toFile()
        try {
            file.writeText(serializedMap)
        } catch (exception: IOException) {
            showErrorDialog("Unable to save the game.")
            return false
        }

        return true
    }

    private fun showErrorDialog(message: String) =
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE)
}

/**
 * Map serialization/deserialization.
 */
private object JsonAdapter {
    private val itemGson = GsonBuilder()
        .registerTypeHierarchyAdapter(Item::class.java, HierarchyAdapter<Item>())
        .create()
    private val baseGson = GsonBuilder()
        .registerTypeHierarchyAdapter(Behaviour::class.java, HierarchyAdapter<Behaviour>())
        .registerTypeHierarchyAdapter(Inventory::class.java, HierarchyAdapter<Inventory>(itemGson))
        .registerTypeHierarchyAdapter(Item::class.java, HierarchyAdapter<Item>())
        .create()
    val gameObjectGson: Gson = GsonBuilder()
        .registerTypeHierarchyAdapter(GameObject::class.java, HierarchyAdapter<GameObject>(baseGson))
        .create()

    private class HierarchyAdapter<T : Any>(private val baseGson: Gson = Gson()) : JsonSerializer<T>, JsonDeserializer<T> {
        override fun serialize(
            gameObject: T,
            interfaceType: Type,
            context: JsonSerializationContext
        ): JsonElement {
            val wrapper = JsonObject()
            wrapper.addProperty("type", gameObject::class.java.name)
            wrapper.add("data", baseGson.toJsonTree(gameObject))
            return wrapper
        }

        override fun deserialize(
            elem: JsonElement,
            interfaceType: Type,
            context: JsonDeserializationContext
        ): T {
            val wrapper = elem as JsonObject
            val typeName = get(wrapper, "type")
            val data = get(wrapper, "data")
            val actualType = typeForName(typeName)
            return baseGson.fromJson(data, actualType)
        }
    }

    private fun typeForName(typeElem: JsonElement): Type {
        try {
            return Class.forName(typeElem.asString)
        } catch (e: ClassNotFoundException) {
            throw JsonParseException(e)
        }
    }

    private operator fun get(wrapper: JsonObject, memberName: String): JsonElement {
        return wrapper.get(memberName)
            ?: throw JsonParseException("No $memberName member found in what was expected to be an interface wrapper")
    }
}