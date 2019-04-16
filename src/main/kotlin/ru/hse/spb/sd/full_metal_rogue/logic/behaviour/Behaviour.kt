package ru.hse.spb.sd.full_metal_rogue.logic.behaviour

import ru.hse.spb.sd.full_metal_rogue.logic.map.*

interface Behaviour {
    fun makeMove(currentPosition: Position, map: GameMap): Position
}

object PassiveBehaviour : Behaviour {
    override fun makeMove(currentPosition: Position, map: GameMap): Position = currentPosition
}

object AggressiveBehaviour : Behaviour {
    override fun makeMove(currentPosition: Position, map: GameMap): Position {
        val distances = map.calculateDistancesFrom(map.playerPosition())
        val possibleMoves = possibleMoves(currentPosition, map)
        var intendedMove = possibleMoves[0]

        for (move in possibleMoves)
            if (distances[move.x][move.y] <= distances[intendedMove.x][intendedMove.y])
                intendedMove = move

        return intendedMove
    }
}

object CowardBehaviour : Behaviour {
    override fun makeMove(currentPosition: Position, map: GameMap): Position {
        val distances = map.calculateDistancesFrom(map.playerPosition())
        val possibleMoves = possibleMoves(currentPosition, map)
        var intendedMove = possibleMoves[0]

        for (move in possibleMoves)
            if (distances[move.x][move.y] >= distances[intendedMove.x][intendedMove.y])
                intendedMove = move

        return intendedMove
    }
}

fun possibleMoves(position: Position, map: GameMap): List<Position> {
    val possibleMoves = mutableListOf(position)

    for (direction in Direction.values()) {
        val nextPosition = position.goToDirection(direction)

        if (map.canPassThrough(nextPosition))
            possibleMoves.add(nextPosition)
    }

    return possibleMoves
}