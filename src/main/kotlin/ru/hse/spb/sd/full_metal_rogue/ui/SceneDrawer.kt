package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.view.*
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

    fun draw(view: View) {
        when (view) {
            is LevelView -> drawLevelScene(view)
            is InventoryView -> drawInventoryScene(view)
            is ChestView -> drawChestScene(view)
            is DeathView -> drawDeathScene(view)
            is StartView -> drawStartScene(view)
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

    private fun drawLevelScene(scene: LevelView) {
        levelSceneUIDrawer.clear()
        levelSceneUIDrawer.drawMap(scene.map)
        levelSceneUIDrawer.outputMessage(scene.message)
        levelSceneUIDrawer.outputPlayerState(getPlayerFromMap(scene.map))
    }

    private fun drawInventoryScene(scene: InventoryView) {
        inventorySceneUIDrawer.clear()
        inventorySceneUIDrawer.outputHeader("Inventory")
        inventorySceneUIDrawer.outputInventory(scene.inventoryItems, scene.equippedItems)
    }

    private fun drawChestScene(scene: ChestView) {
        chestSceneUIDrawer.clear()
        chestSceneUIDrawer.outputHeader("Chest")
        chestSceneUIDrawer.outputChest(scene.chestItems)
    }

    private fun drawDeathScene(scene: DeathView) {
        deathSceneUIDrawer.clear()
        deathSceneUIDrawer.outputDeathMessage(scene.player)
    }

    private fun drawStartScene(scene: StartView) {
        startSceneUIDrawer.clear()
        startSceneUIDrawer.outputStartMessage(scene.mainMenu.currentItemIndex())
    }
}