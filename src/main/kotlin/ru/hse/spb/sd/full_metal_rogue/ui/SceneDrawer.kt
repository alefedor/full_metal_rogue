package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.map.player
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
    private val gameSceneUIDrawer = GameListSceneUIDrawer(terminal)

    fun draw(view: View) {
        when (view) {
            is LevelView -> drawLevelScene(view)
            is InventoryView -> drawInventoryScene(view)
            is ChestView -> drawChestScene(view)
            is DeathView -> drawDeathScene(view)
            is StartView -> drawStartScene(view)
            is GameListView -> drawGameListView(view)
            else -> return
        }
    }

    private fun drawLevelScene(scene: LevelView) {
        levelSceneUIDrawer.clear()
        levelSceneUIDrawer.drawMap(scene.map, scene.chosenPlayerName)
        levelSceneUIDrawer.outputMessage(scene.message)
        levelSceneUIDrawer.outputPlayerState(scene.map.player(scene.chosenPlayerName))
        levelSceneUIDrawer.outputCurrentTurnHolder(scene.currentPlayerName)
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
        startSceneUIDrawer.outputGameTitle()
        when {
            scene.isSinglePlayerMenu() -> {
                startSceneUIDrawer.outputMenuItems(
                    scene.mainMenu.currentItemIndex(),
                    startSceneUIDrawer.singlePlayerOptions
                )
            }
            scene.isMultiPlayerMenu() -> {
                startSceneUIDrawer.outputMenuItems(
                    scene.mainMenu.currentItemIndex(),
                    startSceneUIDrawer.multiPlayerOptions
                )
            }
            else -> {
                startSceneUIDrawer.outputMenuItems(
                    scene.mainMenu.currentItemIndex(),
                    startSceneUIDrawer.defaultMenuOptions
                )
            }
        }
        startSceneUIDrawer.outputHelpMessage()
    }

    private fun drawGameListView(view: GameListView) {
        gameSceneUIDrawer.clear()
        gameSceneUIDrawer.outputGamesList(view.gameListMenu.currentItemIndex(), view.gameListMenu.getItemsList())
    }
}