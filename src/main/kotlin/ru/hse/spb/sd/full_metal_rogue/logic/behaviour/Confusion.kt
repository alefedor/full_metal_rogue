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
        private val MIN_CONFUSION_TIME_MILLISECONDS = TimeUnit.SECONDS.toMillis(20).toInt()
        private val MAX_CONFUSION_TIME_MILLISECONDS = TimeUnit.MINUTES.toMillis(1).toInt()
    }

    private val timer = Timer(
        (MIN_CONFUSION_TIME_MILLISECONDS..MAX_CONFUSION_TIME_MILLISECONDS).random(),
        ActionListener { }
    )

    init {
        timer.isRepeats = false
        timer.start()
    }


    override fun makeMove(currentPosition: Position, map: GameMap): Position {
        return if (timer.isRunning) makeConfusedMove(currentPosition, map) else super.makeMove(currentPosition, map)
    }

    private fun makeConfusedMove(currentPosition: Position, map: GameMap): Position {
        val possibleMoves = possibleMoves(currentPosition, map)
        return possibleMoves.random()
    }
}