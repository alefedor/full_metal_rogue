package ru.hse.spb.sd.full_metal_rogue.logic.map

import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.lang.Exception
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
    fun loadMap() = try {
        ObjectInputStream(FileInputStream(SAVE_NAME)).use { it.readObject() as MutableGameMap }
    } catch (exception: Exception) {
        showErrorDialog("Unable to load previous save.")
        null
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
    fun saveMap(map: GameMap) = try {
        ObjectOutputStream(FileOutputStream(SAVE_NAME)).use { it.writeObject(map) }
        true
    } catch (exception: Exception) {
        showErrorDialog("Unable to save the game.")
        false
    }

    private fun showErrorDialog(message: String) =
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE)
}
