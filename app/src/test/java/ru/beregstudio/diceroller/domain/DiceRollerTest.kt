package ru.beregstudio.diceroller.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class DiceRollerTest {

    private val diceRoller = DiceRoller()

    @Test
    fun `roll D6 returns value between 1 and 6`() {
        repeat(100) {
            val result = diceRoller.roll(6)
            assertTrue("Result $result should be between 1 and 6", result in 1..6)
        }
    }

    @Test
    fun `roll D20 returns value between 1 and 20`() {
        repeat(100) {
            val result = diceRoller.roll(20)
            assertTrue("Result $result should be between 1 and 20", result in 1..20)
        }
    }

    @Test(expected = IllegalArgumentException::class)
    fun `roll with zero sides throws exception`() {
        diceRoller.roll(0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `roll with negative sides throws exception`() {
        diceRoller.roll(-5)
    }
}
