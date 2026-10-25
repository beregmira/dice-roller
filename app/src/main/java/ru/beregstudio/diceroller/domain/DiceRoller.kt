package ru.beregstudio.diceroller.domain

import kotlin.random.Random

/**
 * Доменный компонент, отвечающий за логику броска кубика.
 * Работает независимо от Android (View/Compose).
 */
class DiceRoller(private val random: Random = Random.Default) {

    /**
     * Выполняет бросок кубика.
     * @param numSides Количество граней кубика.
     * @return Случайное значение от 1 до numSides.
     */
    fun roll(numSides: Int): Int {
        require(numSides > 0) { "Количество граней должно быть больше 0" }
        return random.nextInt(numSides) + 1
    }
}

/**
 * Результат броска, содержащий информацию о выпавшем значении.
 */
data class RollResult(
    val value: Int,
    val numSides: Int
)

/**
 * Типы кубиков, которые будут поддерживаться в будущем (Фаза 2).
 */
enum class DiceType(val sides: Int) {
    D6(6),
    D20(20); // Задел на будущее
}
