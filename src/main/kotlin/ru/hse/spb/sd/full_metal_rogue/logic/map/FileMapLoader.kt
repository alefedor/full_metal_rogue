package ru.hse.spb.sd.full_metal_rogue.logic.map

import com.google.gson.*
import ru.hse.spb.sd.full_metal_rogue.Game
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
    const val SAVE_NAME = "save.txt"
    private val gson = GsonBuilder()
        .registerTypeHierarchyAdapter(GameObject::class.java, JsonAdapter.GameObjectAdapter())
        .create()

    /**
     * Loads map from the save file.
     */
    fun loadMap(): MutableGameMap? {
        val file = Paths.get(SAVE_NAME).toFile()
        return try {
            gson.fromJson(file.readText(), MutableGameMap::class.java)
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
        val serializedMap = gson.toJson(map)
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

private object JsonAdapter {
    class GameObjectAdapter : JsonSerializer<GameObject>, JsonDeserializer<GameObject> {
        private val gson = GsonBuilder()
            .registerTypeHierarchyAdapter(Behaviour::class.java, HierarchyAdapter<Behaviour>())
            .registerTypeHierarchyAdapter(Inventory::class.java, HierarchyAdapter<Inventory>())
            .registerTypeHierarchyAdapter(Item::class.java, HierarchyAdapter<Item>())
            .create()

        override fun serialize(
            gameObject: GameObject,
            interfaceType: Type,
            context: JsonSerializationContext
        ): JsonElement {
            val wrapper = JsonObject()
            wrapper.addProperty("type", gameObject::class.java.name)
            wrapper.add("data", gson.toJsonTree(gameObject))
            return wrapper
        }

        override fun deserialize(
            elem: JsonElement,
            interfaceType: Type,
            context: JsonDeserializationContext
        ): GameObject {
            val wrapper = elem as JsonObject
            val typeName = get(wrapper, "type")
            val data = get(wrapper, "data")
            val actualType = typeForName(typeName)
            return gson.fromJson(data, actualType)
        }
    }

    class HierarchyAdapter<T : Any> : JsonSerializer<T>, JsonDeserializer<T> {
        override fun serialize(
            gameObject: T,
            interfaceType: Type,
            context: JsonSerializationContext
        ): JsonElement {
            val wrapper = JsonObject()
            wrapper.addProperty("type", gameObject::class.java.name)
            wrapper.add("data", Gson().toJsonTree(gameObject))
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
            return Gson().fromJson(data, actualType)
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