package ru.hse.spb.sd.full_metal_rogue.logic.behaviour

import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.Position

/**
 * Decorates a Behaviour object, overriding its makeMove() method.
 * @see [Behaviour.makeMove]
 */
abstract class BehaviourDecorator(private val behaviour: Behaviour) : Behaviour {
    override fun makeMove(currentPosition: Position, map: GameMap): Position {
        return behaviour.makeMove(currentPosition, map)
    }

    override fun simplify(): Behaviour = if (stillDecorated()) this else behaviour.simplify()

    protected abstract fun stillDecorated(): Boolean
}

/**
 * Makes the next move random for a random number of game turns,
 * then restores the previous behaviour.
 */
class ConfusionDecorator(behaviour: Behaviour) : BehaviourDecorator(behaviour) {
    companion object {
        private const val MIN_CONFUSED_TURNS_COUNT = 5
        private const val MAX_CONFUSED_TURNS_COUNT = 20
    }

    private val confusedTurnsCount = getConfusedTurnsCount()
    private var turnsCount = 0

    override fun makeMove(currentPosition: Position, map: GameMap): Position {
        return if (turnsCount++ < confusedTurnsCount) {
            makeConfusedMove(currentPosition, map)
        } else {
            super.makeMove(currentPosition, map)
        }
    }

    override fun stillDecorated(): Boolean = turnsCount < confusedTurnsCount

    private fun makeConfusedMove(currentPosition: Position, map: GameMap): Position {
        val possibleMoves = possibleMoves(currentPosition, map)
        return possibleMoves.random()
    }

    private fun getConfusedTurnsCount() = (MIN_CONFUSED_TURNS_COUNT..MAX_CONFUSED_TURNS_COUNT).random()
}