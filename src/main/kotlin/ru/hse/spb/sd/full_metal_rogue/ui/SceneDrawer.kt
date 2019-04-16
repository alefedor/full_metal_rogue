package ru.hse.spb.sd.full_metal_rogue.ui

import asciiPanel.AsciiPanel
import ru.hse.spb.sd.full_metal_rogue.logic.map.GameMap
import ru.hse.spb.sd.full_metal_rogue.logic.objects.Player
import ru.hse.spb.sd.full_metal_rogue.scene.*


class SceneDrawer(terminal: AsciiPanel) {
    private val drawer = UIDrawer(terminal)

    private fun getPlayerFromMap(map: GameMap): Player {
        for (i in 0 until map.height) {
            for (j in 0 until map.width) {
                if (map[j, i] is Player) {
                    return map[j, i] as Player
                }
            }
        }

        throw NoPlayerOnMapException()
    }

    private fun drawLevelScene(scene: LevelScene) {
        drawer.drawMap(scene.map)
        val message = if (scene.messages.isEmpty()) "" else scene.messages[0]
        drawer.outputMessage(message)
        drawer.outputPlayerState(getPlayerFromMap(scene.map))
    }

    private fun drawInventoryScene(scene: InventoryScene) {}

    private fun drawChestScene(scene: ChestScene) {}

    private fun drawDeathScene(scene: DeathScene) {}

    private fun drawStartScene(scene: StartScene) {
        drawer.outputStartMessage()
    }

    fun draw(scene: Scene) {
        drawer.clear()

        when (scene) {
            is LevelScene -> drawLevelScene(scene)
            is InventoryScene -> drawInventoryScene(scene)
            is ChestScene -> drawChestScene(scene)
            is DeathScene -> drawDeathScene(scene)
            is StartScene -> drawStartScene(scene)
            else -> return
        }
    }
}