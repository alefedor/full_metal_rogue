package ru.hse.spb.sd.full_metal_rogue.logic.objects

import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.Behaviour
import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.ConfusionDecorator
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.Position
import java.awt.Color

/**
 * Represents an enemy which the current player can attack and take damage from.
 */
class Enemy(
    maxHealth: Int,
    attackPower: Int,
    private var behaviour: Behaviour,
    name: String,
    experienceCost: Int,
    private var chest: Chest,
    val color: Color = Color.RED
) : Actor(maxHealth, attackPower, name, experienceCost) {
    /**
     * Moves the enemy according to its behavior.
     */
    fun makeMove(currentPosition: Position, map: GameMap): Position = behaviour.makeMove(currentPosition, map)

    /**
     * Confuses the enemy, causing it to move in random directions for several next turns.
     */
    fun getConfused() {
        if (behaviour !is ConfusionDecorator) {
            behaviour = ConfusionDecorator(behaviour)
        } else {
            (behaviour as ConfusionDecorator).renewConfusion()
        }
    }

    override fun die(): Chest? {
        return if (chest.items.isEmpty()) null else chest
    }

    /**
     * Takes all items from a chest.
     */
    fun absorbChest(absorbedChest: Chest) {
        chest.items.addAll(absorbedChest.items)
    }
}