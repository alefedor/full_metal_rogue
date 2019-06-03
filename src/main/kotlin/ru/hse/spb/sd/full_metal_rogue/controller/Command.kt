package ru.hse.spb.sd.full_metal_rogue.controller

import ru.hse.spb.sd.full_metal_rogue.logic.map.Direction

/**
 * Represents player's command.
 */
interface Command

class DirectionCommand(val direction: Direction) : Command

object SelectCommand : Command

object BackCommand : Command

object IdleCommand : Command