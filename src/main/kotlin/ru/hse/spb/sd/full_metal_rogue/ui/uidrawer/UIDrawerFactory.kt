package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer

/**
 * Implementation of Abstract Factory pattern for UI Drawers.
 */
interface UIDrawerFactory {
    fun chestSceneUIDrawer(): ChestSceneUIDrawer

    fun gameListSceneUIDrawer(): GameListSceneUIDrawer

    fun inventorySceneUIDrawer(): InventorySceneUIDrawer

    fun levelSceneUIDrawer(): LevelSceneUIDrawer

    fun startSceneUIDrawer(): StartSceneUIDrawer

    fun deathSceneUIDrawer(): DeathSceneUIDrawer
}