package ru.hse.spb.sd.full_metal_rogue.view

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item

/**
 * Representation of player's inventory.
 */
class InventoryView(val inventoryItems: Menu<Item>,
                    val equippedItems: List<Item>
) : View