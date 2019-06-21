package ru.hse.spb.sd.full_metal_rogue.logic.behaviour

import ru.hse.spb.sd.full_metal_rogue.logic.map.*
import java.io.Serializable

/**
 * Interface for different behaviours of actors.
 */
interface Behaviour : Serializable {
    fun makeMove(currentPosition: Position, map: GameMap): Position
    fun simplify(): Behaviour = this
}

/**
 * A strategy for an actor which makes him stay in the same place.
 */
object PassiveBehaviour : Behaviour {
    override fun makeMove(currentPosition: Position, map: GameMap): Position = currentPosition
}

/**
 * A strategy for an actor which makes him do go to a player position.
 */
object AggressiveBehaviour : Behaviour {
    override fun makeMove(currentPosition: Position, map: GameMap): Position {
        val distances = map.calculateDistancesFrom(map.playerPositions())
        val aggressiveMove = possibleMoves(currentPosition, map).minWith(compareBy({distances[it.x][it.y]}))

        return aggressiveMove!!
    }
}

/**
 * A strategy for an actor which makes him do run from a player position.
 */
object CowardBehaviour : Behaviour {
    override fun makeMove(currentPosition: Position, map: GameMap): Position {
        val distances = map.calculateDistancesFrom(map.playerPositions())
        val cowardMove = possibleMoves(currentPosition, map).maxWith(compareBy({distances[it.x][it.y]}))

        return cowardMove!!
    }
}

/**
 * Returns List of possible moves from a position in a map.
 */
fun possibleMoves(position: Position, map: GameMap): List<Position> {
    val possibleMoves = mutableListOf(position)

    for (direction in Direction.values()) {
        val nextPosition = apply(position, direction)

        if (map.canPassThrough(nextPosition))
            possibleMoves.add(nextPosition)
    }

    return possibleMoves
}