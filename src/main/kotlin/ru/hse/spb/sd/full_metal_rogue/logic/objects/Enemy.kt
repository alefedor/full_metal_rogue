package ru.hse.spb.sd.full_metal_rogue.logic.objects

import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.Behaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.ConfusionDecorator
import ru.hse.spb.sd.full_metal_rogue.logic.inventory.ItemDropper
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.Position

class Enemy(
    maxHealth: Int,
    attackPower: Int,
    private var behaviour: Behaviour,
    val name: String,
    val experienceCost: Int,
    private val itemDroppers: List<ItemDropper>
) : Actor(maxHealth, attackPower) {
    fun makeMove(currentPosition: Position, map: GameMap): Position = behaviour.makeMove(currentPosition, map)

    fun getConfused() {
        behaviour = ConfusionDecorator(behaviour)
    }

    fun die(): Chest {
        return Chest(emptyList())
    }
}