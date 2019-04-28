package ru.hse.spb.sd.full_metal_rogue.scene.handler

import ru.hse.spb.sd.full_metal_rogue.logic.level.StandardLevelGenerator
import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction
import ru.hse.spb.sd.full_metal_rogue.logic.map.FileMapLoader
import ru.hse.spb.sd.full_metal_rogue.scene.MutableMenu
import ru.hse.spb.sd.full_metal_rogue.scene.StartScene
import ru.hse.spb.sd.full_metal_rogue.ui.SceneDrawer
import kotlin.system.exitProcess

/**
 * Handles user input on a StartScene
 */
class StartSceneHandler(private val sceneDrawer: SceneDrawer) : SceneHandler(sceneDrawer) {
    private val menu = MutableMenu(mutableListOf(MainMenuItem.CONTINUE, MainMenuItem.NEW_GAME))
    override val scene
        get() = StartScene(menu)

    /**
     * Exits the game.
     */
    override fun backAction() = exitProcess(0)

    /**
     * Changes current main menu item.
     */
    override fun directionAction(direction: Direction): SceneHandler? {
        when(direction) {
            Direction.UP -> menu.toPreviousItem()
            Direction.DOWN -> menu.toNextItem()
        }
        return this
    }

    /**
     * Selects current main menu item.
     */
    override fun selectAction(): SceneHandler? =
        when(menu.currentItem()) {
            MainMenuItem.NEW_GAME -> LevelSceneHandler(sceneDrawer, StandardLevelGenerator().generateLevel())
            MainMenuItem.CONTINUE -> {
                val map = FileMapLoader.loadMap()
                if(map != null) LevelSceneHandler(sceneDrawer, map) else this
            }
        }

    enum class MainMenuItem {
        NEW_GAME, CONTINUE
    }
}