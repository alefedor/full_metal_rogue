package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.scene.*
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.*

/**
 * Draws scenes in terminal.
 */
class SceneDrawer(terminal: AsciiPanel) {
    private val levelSceneUIDrawer = LevelSceneUIDrawer(terminal)
    private val inventorySceneUIDrawer = InventorySceneUIDrawer(terminal)
    private val chestSceneUIDrawer = ChestSceneUIDrawer(terminal)
    private val deathSceneUIDrawer = DeathSceneUIDrawer(terminal)
    private val startSceneUIDrawer = StartSceneUIDrawer(terminal)

    fun draw(scene: Scene) {
        when (scene) {
            is LevelScene -> drawLevelScene(scene)
            is InventoryScene -> drawInventoryScene(scene)
            is ChestScene -> drawChestScene(scene)
            is DeathScene -> drawDeathScene(scene)
            is StartScene -> drawStartScene(scene)
            else -> return
        }
    }

    private fun getPlayerFromMap(map: GameMap): Player {
        for (i in 0 until map.height) {
            for (j in 0 until map.width) {
                if (map[j, i] is Player) {
                    return map[j, i] as Player
                }
            }
        }

        throw NoPlayerOnMapException()
    }

    private fun drawLevelScene(scene: LevelScene) {
        levelSceneUIDrawer.clear()
        levelSceneUIDrawer.drawMap(scene.map)
        levelSceneUIDrawer.outputMessage(scene.message)
        levelSceneUIDrawer.outputPlayerState(getPlayerFromMap(scene.map))
    }

    private fun drawInventoryScene(scene: InventoryScene) {
        inventorySceneUIDrawer.clear()
        inventorySceneUIDrawer.outputHeader("Inventory")
        inventorySceneUIDrawer.outputInventory(scene.inventoryItems, scene.equippedItems)
    }

    private fun drawChestScene(scene: ChestScene) {
        chestSceneUIDrawer.clear()
        chestSceneUIDrawer.outputHeader("Chest")
        chestSceneUIDrawer.outputChest(scene.chestItems)
    }

    private fun drawDeathScene(scene: DeathScene) {
        deathSceneUIDrawer.clear()
        deathSceneUIDrawer.outputDeathMessage(scene.player)
    }

    private fun drawStartScene(scene: StartScene) {
        startSceneUIDrawer.clear()
        startSceneUIDrawer.outputStartMessage(scene.mainMenu.currentItemIndex())
    }
}