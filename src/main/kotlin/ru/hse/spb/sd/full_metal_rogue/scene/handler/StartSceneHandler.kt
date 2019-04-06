package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.map.StandardLevelGenerator
import ru.hse.spb.sd.full_metal_rogue.map.FileMapLoader
import ru.hse.spb.sd.full_metal_rogue.scene.StartScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import java.awt.event.KeyEvent
import java.awt.event.KeyEvent.*
import kotlin.system.exitProcess

class StartSceneHandler(private val sceneDrawer: SceneDrawer) : SceneHandler(sceneDrawer) {
    override val scene
        get() = StartScene()

    override fun handleUserInput(key: KeyEvent): SceneHandler? =
        when (key.keyCode) {
            VK_ESCAPE -> exitProcess(0)
            VK_1 -> LevelSceneHandler(sceneDrawer, StandardLevelGenerator().generateLevel())
            VK_2 -> {
                val map = FileMapLoader.loadMap()
                if(map != null) LevelSceneHandler(sceneDrawer, map) else this
            }
            else -> this
        }
}