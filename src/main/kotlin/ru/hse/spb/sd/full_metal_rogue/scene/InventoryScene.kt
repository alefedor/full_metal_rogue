package ru.hse.spb.sd.full_metal_rogue.scene

import ru.hse.spb.sd.full_metal_rogue.logic.inventory.Item

class InventoryScene(val inventoryItems: Menu<Item>,
                     val equipedItems: Menu<Item>
) : Scene