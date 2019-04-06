package ru.hse.spb.sd.full_metal_rogue.map

import ru.hse.spb.sd.full_metal_rogue.objects.GameObject
import ru.hse.spb.sd.full_metal_rogue.objects.Wall

/**
 * Abstract class for storing information about game map and its contents
 */
abstract class GameMap(val width: Int, val height: Int) {
    protected val objects = Array(width) { Array<GameObject>(height) { Wall } }

    operator fun get(x: Int, y: Int): GameObject = objects[x][y]
}

/**
 *  Class for storing and editing information about game map and its contents
 */
class MutableGameMap(width: Int, height: Int): GameMap(width, height) {
    operator fun set(x: Int, y: Int, gameObject: GameObject) {
        objects[x][y] = gameObject
    }
}