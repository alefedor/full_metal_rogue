package ru.hse.spb.sd.full_metal_rogue.objects

class Enemy(
    maxHealth: Int,
    attackPower: Int,
    val experienceCost: Int,
    val name: String
) : Actor(maxHealth, attackPower)