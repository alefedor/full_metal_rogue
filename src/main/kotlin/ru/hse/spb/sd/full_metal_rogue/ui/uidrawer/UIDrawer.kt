package ru.hse.spb.sd.full_metal_rogue.ui.uidrawer

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.*
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.objects.*
import ru.hse.spb.sd.full_metal_rogue.view.Menu

/**
 * An abstraction of UI drawer for scenes
 */
interface UIDrawer {
    /**
     * Clears current view
     */
    fun clear()

    /**
     * Outputs header
     */
    fun outputHeader(header: String)
}

/**
 * Handles drawing for ChestView.
 */
interface ChestSceneUIDrawer : UIDrawer {
    /**
     * Outputs a chest item menu.
     */
    fun outputChest(chestItemMenu: Menu<Item>)
}

/**
 * Handles drawing for DeathView.
 */
interface DeathSceneUIDrawer : UIDrawer {
    /**
     * Outputs a message in case of player death.
     * The message includes the final player state.
     */
    fun outputDeathMessage(player: Player)
}

/**
 * Handles drawing for GameListView.
 */
interface GameListSceneUIDrawer : UIDrawer {
    /**
     * Outputs available games menu and a matching title.
     */
    fun outputGamesList(currentPosition: Int, gameNames: List<String>)
}

/**
 * Handles drawing for InventoryView.
 */
interface InventorySceneUIDrawer : UIDrawer {
    /**
     * Outputs inventory item menu and equipped items.
     */
    fun outputInventory(inventoryItems: Menu<Item>, equippedItems: List<Item>)
}

/**
 * Handles drawing for LevelView.
 */
interface LevelSceneUIDrawer : UIDrawer {
    /**
     * Draws the game map.
     */
    fun drawMap(map: GameMap, currentPlayerName: String)

    /**
     * Outputs player state.
     * The state consists of a characteristic and its value.
     */
    fun outputPlayerState(player: Player)

    /**
     * Outputs which player's turn.
     */
    fun outputCurrentTurnHolder(currentPlayerName: String)

    /**
     * Outputs a message in the log.
     */
    fun outputMessage(message: String)
}

/**
 * Handles drawing for StartView.
 */
interface StartSceneUIDrawer : UIDrawer {
    /**
     * Outputs game title.
     */
    fun outputWelcomeMessage()

    /**
     * Outputs game controls hints.
     */
    fun outputHelpMessage()

    /**
     * Outputs start scene menu.
     */
    fun outputMenuItems(currentPosition: Int, options: List<String>)
}