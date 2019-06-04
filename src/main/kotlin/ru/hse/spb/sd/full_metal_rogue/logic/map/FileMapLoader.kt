package ru.hse.spb.sd.full_metal_rogue.logic.map

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
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
        val oos = ObjectInputStream(FileInputStream(SAVE_NAME))
        return oos.readObject() as MutableGameMap
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
        val ois = ObjectOutputStream(FileOutputStream(SAVE_NAME))
        ois.writeObject(map)


        /*
        val file = Paths.get(SAVE_NAME).toFile()
        try {
            file.writeText(serializedMap)
        } catch (exception: Exception) {
            showErrorDialog("Unable to save the game.")
            return false
        }*/

        return true
    }

    private fun showErrorDialog(message: String) =
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE)
}
