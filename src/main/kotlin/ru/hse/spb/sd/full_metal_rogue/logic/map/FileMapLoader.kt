package ru.hse.spb.sd.full_metal_rogue.logic.map

import com.google.gson.*
import java.io.IOException
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import java.lang.reflect.Type
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*


object FileMapLoader {
    private val gson = GsonBuilder()
        .registerTypeHierarchyAdapter(GameObject::class.java, GameObjectAdapter())
        .create()

    fun loadMap(): MutableGameMap? {
        val fileChooser = JFileChooser()
        val returnValue = fileChooser.showOpenDialog(null)
        return try {
            when (returnValue) {
                JFileChooser.APPROVE_OPTION -> gson.fromJson(fileChooser.selectedFile.readText(), MutableGameMap::class.java)
                JFileChooser.ERROR_OPTION -> null.also { showErrorDialog("Unable to select file.") }
                else -> null
            }
        } catch (exception: IOException) {
            showErrorDialog("Unable to read file.")
            null
        } catch (exception: JsonSyntaxException) {
            showErrorDialog("Malformed map.")
            null
        }
    }

    fun saveMap(map: GameMap) {
        val serializedMap = gson.toJson(map)
        val fileChooser = JFileChooser()
        val returnValue = fileChooser.showSaveDialog(null)

        try {
            when (returnValue) {
                JFileChooser.APPROVE_OPTION -> fileChooser.selectedFile.writeText(serializedMap)
                JFileChooser.ERROR_OPTION -> showErrorDialog("Unable to select file.")
                else -> {}
            }
        } catch (exception: IOException) {
            showErrorDialog("Unable to write to file.")
        }
    }

    private fun showErrorDialog(message: String) =
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE)

    private class GameObjectAdapter : JsonSerializer<GameObject>, JsonDeserializer<GameObject> {
        override fun serialize(gameObject: GameObject, interfaceType: Type, context: JsonSerializationContext): JsonElement {
            val wrapper = JsonObject()
            wrapper.addProperty("type", gameObject::class.java.name)
            wrapper.add("data", Gson().toJsonTree(gameObject))
            return wrapper
        }

        override fun deserialize(elem: JsonElement, interfaceType: Type, context: JsonDeserializationContext): GameObject {
            val wrapper = elem as JsonObject
            val typeName = get(wrapper, "type")
            val data = get(wrapper, "data")
            val actualType = typeForName(typeName)
            return Gson().fromJson(data, actualType)
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
}