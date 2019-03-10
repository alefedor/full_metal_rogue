package ru.hse.spb.sd.full_metal_rogue.objects

class Player(maxHealth: Int, attackPower: Int) : Actor(maxHealth, attackPower) {
    private var totalExperience = 0

    fun earnExperience(experience: Int) {
        totalExperience += experience
    }
}