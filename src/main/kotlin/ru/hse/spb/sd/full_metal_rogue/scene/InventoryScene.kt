package ru.hse.spb.sd.full_metal_rogue.scene

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item

/**
 * Representation of player's inventory.
 */
class InventoryScene(val inventoryItems: Menu<Item>,
                     val equippedItems: List<Item>
) : Scene