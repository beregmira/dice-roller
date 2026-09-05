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
    private val contextParam: Context,
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
     * Uses `context.resources.getIdentifier()` for dynamic resource resolution.
     * 
     * The image names follow the pattern: set_{diceSet}_dice_{face}
     * Example: set_1_dice_3 refers to the 3rd face of the 1st skin.
     */
    private fun getDrawableResId(face: Int): Int {
        val resName = "set_${diceSet}_dice_$face"
        return contextParam.resources.getIdentifier(resName, "drawable", contextParam.packageName)
    }
}