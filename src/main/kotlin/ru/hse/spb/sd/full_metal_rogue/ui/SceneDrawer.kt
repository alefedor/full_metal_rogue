package ru.hse.spb.sd.full_metal_rogue.ui

import ru.hse.spb.sd.full_metal_rogue.scene.*


class SceneDrawer(private val drawer: UIDrawer) {

    private fun draw(scene: LevelScene) {
        drawer.drawMap(scene.map)
        drawer.outputMessage(scene.message)
        //TODO maybe you should look for player on the map by yourself
        drawer.outputPlayerState(scene.player)
    }

    private fun draw(scene: InventoryScene) {}

    private fun draw(scene: ChestScene) {}

    private fun draw(scene: DeathScene) {}

    fun draw(scene: Scene) {
        drawer.clear()
    }
}