package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.map.CaveLevelGenerator
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
            VK_ESCAPE -> exitProcess(1)
            VK_1 -> LevelSceneHandler(sceneDrawer, CaveLevelGenerator.generateLevel())
            //TODO reading map from file
            //VK_2 ->
            else -> this
        }

}