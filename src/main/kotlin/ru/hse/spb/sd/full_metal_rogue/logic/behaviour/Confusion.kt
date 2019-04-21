package ru.hse.spb.sd.full_metal_rogue.logic.behaviour

import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.map.Position
import java.awt.event.ActionListener
import java.util.concurrent.TimeUnit
import javax.swing.Timer

abstract class BehaviourDecorator(private val behaviour: Behaviour) : Behaviour {
    override fun makeMove(currentPosition: Position, map: GameMap): Position {
        return behaviour.makeMove(currentPosition, map)
    }
}

class ConfusionDecorator(behaviour: Behaviour) : BehaviourDecorator(behaviour) {
    companion object {
        private const val MIN_CONFUSED_TURNS_COUNT = 5
        private const val MAX_CONFUSED_TURNS_COUNT = 20
    }

    private val confusedTurnsCount = (MIN_CONFUSED_TURNS_COUNT..MAX_CONFUSED_TURNS_COUNT).random()
    private var turnsCount = 0


    override fun makeMove(currentPosition: Position, map: GameMap): Position {
        return if (turnsCount++ < confusedTurnsCount) {
            makeConfusedMove(currentPosition, map)
        } else {
            super.makeMove(currentPosition, map)
        }
    }

    private fun makeConfusedMove(currentPosition: Position, map: GameMap): Position {
        val possibleMoves = possibleMoves(currentPosition, map)
        return possibleMoves.random()
    }
}