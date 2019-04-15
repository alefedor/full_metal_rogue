package ru.hse.spb.sd.full_metal_rogue.logic.objects

import ru.hse.spb.sd.full_metal_rogue.logic.behaviour.Behaviour
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.Position

class Enemy(
    maxHealth: Int,
    attackPower: Int,
    private val behaviour: Behaviour,
    val name: String
) : Actor(maxHealth, attackPower) {
    fun makeMove(currentPosition: Position, map: GameMap): Position = behaviour.makeMove(currentPosition, map)
}