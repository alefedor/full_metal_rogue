package ru.hse.spb.sd.full_metal_rogue.logic.objects

import kotlin.math.roundToInt

class Player(maxHealth: Int, attackPower: Int) : Actor(maxHealth, attackPower) {
    companion object {
        const val NEEDED_EXPERIENCE_RISE = 1.2
        const val STATS_RISE = 1.11
    }

    var nextLevelMark = 10

    var experience = 0
        private set

    var level = 0
        protected set

    fun earnExperience(experience: Int): Boolean {
        this.experience += experience
        return checkLevelUp()
    }

    private fun checkLevelUp(): Boolean {
        var levelUp = false

        while (experience >= nextLevelMark) {
            levelUp = true
            level++
            experience -= nextLevelMark
            nextLevelMark = (nextLevelMark * NEEDED_EXPERIENCE_RISE).roundToInt()
            maxHealth = (maxHealth * STATS_RISE).roundToInt()
            attackPower = (attackPower * STATS_RISE).roundToInt()
            currentHealth = maxHealth // instant healing on levelling up
        }

        return levelUp
    }
}