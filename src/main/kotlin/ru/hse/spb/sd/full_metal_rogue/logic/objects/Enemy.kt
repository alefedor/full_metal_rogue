package ru.hse.spb.sd.full_metal_rogue.logic.objects

import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.Behaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.ConfusionDecorator
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.Position

class Enemy(
    maxHealth: Int,
    attackPower: Int,
    private var behaviour: Behaviour,
    name: String,
    experienceCost: Int,
    private var chest: Chest
) : Actor(maxHealth, attackPower, name, experienceCost) {
    fun makeMove(currentPosition: Position, map: GameMap): Position = behaviour.makeMove(currentPosition, map)

    fun getConfused() {
        behaviour = ConfusionDecorator(behaviour)
    }

    override fun die(): Chest? {
        return if (chest.items.isEmpty()) null else chest
    }

    fun absorbChest(absorbedChest: Chest) {
        chest.items.addAll(absorbedChest.items)
    }
}