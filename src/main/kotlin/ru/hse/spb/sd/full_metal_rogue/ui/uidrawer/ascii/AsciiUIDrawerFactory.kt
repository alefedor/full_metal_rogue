package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.ascii

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.ui.uidrawer.*

/**
 * Implementation of UIDrawerFactory for ascii ui
 */
class AsciiUIDrawerFactory(private val terminal: AsciiPanel) : UIDrawerFactory {
    override fun deathSceneUIDrawer(): DeathSceneUIDrawer = DeathSceneAsciiUIDrawer(terminal)

    override fun chestSceneUIDrawer(): ChestSceneUIDrawer = ChestSceneAsciiUIDrawer(terminal)

    override fun gameListSceneUIDrawer(): GameListSceneUIDrawer = GameListSceneAsciiUIDrawer(terminal)

    override fun inventorySceneUIDrawer(): InventorySceneUIDrawer = InventorySceneAsciiUIDrawer(terminal)

    override fun levelSceneUIDrawer(): LevelSceneUIDrawer = LevelSceneAsciiUIDrawer(terminal)

    override fun startSceneUIDrawer(): StartSceneUIDrawer = StartSceneAsciiUIDrawer(terminal)
}