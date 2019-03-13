package ru.hse.spb.sd.full_metal_rogue.scene.handler

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ru.hse.spb.sd.full_metal_rogue.map.CaveLevelGenerator
import ru.hse.spb.sd.full_metal_rogue.map.MutableGameMap
import ru.hse.spb.sd.full_metal_rogue.scene.StartScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import java.io.IOException
import javax.swing.JFileChooser
import javax.swing.JOptionPane
import kotlin.system.exitProcess

class StartSceneHandler(private val sceneDrawer: SceneDrawer) : SceneHandler(sceneDrawer) {
    override val scene
        get() = StartScene()

    override fun handleUserInput(key: KeyEvent): SceneHandler? =
        when (key.keyCode) {
            VK_ESCAPE -> exitProcess(0)
            VK_1 -> LevelSceneHandler(sceneDrawer, CaveLevelGenerator.generateLevel())
            VK_2 -> loadLevelSceneFromFile()
            else -> this
        }

    private fun loadLevelSceneFromFile(): SceneHandler {
        val fileChooser = JFileChooser()
        val returnValue = fileChooser.showOpenDialog(null)

        try {
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                val serializedMap = fileChooser.selectedFile.readText()
                val gameMap = Gson().fromJson(serializedMap, MutableGameMap::class.java)
                return LevelSceneHandler(sceneDrawer, gameMap)
            } else if (returnValue == JFileChooser.ERROR_OPTION) {
                showErrorDialog("Unable to select file.")
                return this
            }
        } catch (exception: IOException) {
            showErrorDialog("Unable to read file.")
        } catch (exception: JsonSyntaxException) {
            showErrorDialog("Malformed map.")
        }

        return this
    }

    private fun showErrorDialog(message: String) =
        JOptionPane.showMessageDialog(null, message, "", JOptionPane.ERROR_MESSAGE)
}