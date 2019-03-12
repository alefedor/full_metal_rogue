package ru.hse.spb.sd.full_metal_rogue.objects

class Player(maxHealth: Int, attackPower: Int) : Actor(maxHealth, attackPower) {
    var totalExperience = 0
        private set

    fun earnExperience(experience: Int) {
        totalExperience += experience
    }
}