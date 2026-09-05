package ru.beregstudio.diceroller

import android.content.Context
import android.widget.ImageView
import kotlin.random.Random

/**
 * Represents a dice with customizable sides and visual sets.
 *
 * @param numSide The number of sides on the dice.
 * @param diceSet The visual set of dice images to use (1-6, etc.).
 * @param contextParam The context used to create the dice image view.
 *
 * @property diceRoll The current rolled value of the dice (1 to numSide).
 * @property image The ImageView that displays the dice face.
 * @property diceSet The index of the dice set (e.g., 1 for red, 2 for green, etc.).
 */
class Dice(
    private val numSide: Int,
    val diceSet: Int,
    private val contextParam: Context
) {
    val diceRoll = getRandomDice()
    val image = ImageView(contextParam)

    init {
        setDiceImage()
    }

    fun setDiceImage() {
        val resId = getDrawableResId(diceRoll)
        image.setImageResource(resId)
        image.contentDescription = diceRoll.toString()
    }

    /**
     * Returns the resource ID of the current dice face image.
     *
     * @return The resource ID.
     */
    fun getImageResourceId(): Int = getDrawableResId(diceRoll)

    fun setDiceSize() {
        image.layoutParams.width = 350
    }

    private fun getRandomDice(): Int {
        return Random.nextInt(numSide) + 1
    }

    /**
     * Gets the drawable resource ID for the current roll value.
     * 
     * Uses a pre-defined map of resources to avoid dynamic resource lookups.
     *
     * @return The resource ID.
     */
    private fun getDrawableResId(face: Int): Int {
        val skinIds = diceSkins[diceSet] ?: return 0
        return skinIds[face - 1]
    }

    companion object {
        private val diceSkins: Map<Int, List<Int>> = mapOf(
            1 to listOf(R.drawable.set_1_dice_1, R.drawable.set_1_dice_2, R.drawable.set_1_dice_3, R.drawable.set_1_dice_4, R.drawable.set_1_dice_5, R.drawable.set_1_dice_6),
            2 to listOf(R.drawable.set_2_dice_1, R.drawable.set_2_dice_2, R.drawable.set_2_dice_3, R.drawable.set_2_dice_4, R.drawable.set_2_dice_5, R.drawable.set_2_dice_6),
            3 to listOf(R.drawable.set_3_dice_1, R.drawable.set_3_dice_2, R.drawable.set_3_dice_3, R.drawable.set_3_dice_4, R.drawable.set_3_dice_5, R.drawable.set_3_dice_6),
            4 to listOf(R.drawable.set_4_dice_1, R.drawable.set_4_dice_2, R.drawable.set_4_dice_3, R.drawable.set_4_dice_4, R.drawable.set_4_dice_5, R.drawable.set_4_dice_6),
            5 to listOf(R.drawable.set_5_dice_1, R.drawable.set_5_dice_2, R.drawable.set_5_dice_3, R.drawable.set_5_dice_4, R.drawable.set_5_dice_5, R.drawable.set_5_dice_6),
            6 to listOf(R.drawable.set_6_dice_1, R.drawable.set_6_dice_2, R.drawable.set_6_dice_3, R.drawable.set_6_dice_4, R.drawable.set_6_dice_5, R.drawable.set_6_dice_6)
        )
    }
}