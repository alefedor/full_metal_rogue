package ru.hse.spb.sd.full_metal_rogue.logic.map

import ru.hse.spb.sd.full_metal_rogue.logic.objects.GameObject
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Wall
import java.io.Serializable

/**
 * Abstract class for storing information about game map and its contents
 */
abstract class GameMap(val width: Int, val height: Int) : Serializable {
    protected val objects = Array(width) { Array<GameObject>(height) { Wall } }

    operator fun get(x: Int, y: Int): GameObject = objects[x][y]
    operator fun get(p: Position): GameObject = objects[p.x][p.y]
}

/**
 *  Class for storing and editing information about game map and its contents
 */
class MutableGameMap(width: Int, height: Int): GameMap(width, height) {
    operator fun set(x: Int, y: Int, gameObject: GameObject) {
        objects[x][y] = gameObject
    }

    operator fun set(p: Position, gameObject: GameObject) {
        objects[p.x][p.y] = gameObject
    }
}
