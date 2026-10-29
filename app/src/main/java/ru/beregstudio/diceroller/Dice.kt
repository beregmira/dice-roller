package ru.beregstudio.diceroller

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import android.view.View

/**
 * Represents a dice with customizable sides and visual sets.
 *
 * @param faceValue The value shown on the face.
 * @param diceSet The visual set of dice images to use (1-6, etc.).
 * @param contextParam The context used to create the dice image view.
 *
 * @property faceValue The current value of the dice.
 * @property diceSet The index of the dice set (e.g., 1 for red, 2 for green, etc.).
 * @property image The ImageView that displays the dice face.
 */
class Dice(
    val faceValue: Int,
    val diceSet: Int,
    private val contextParam: Context
) {
    val image = ImageView(contextParam)

    init {
        setDiceImage()
    }

    fun setDiceImage() {
        val resId = getDrawableResId(faceValue)
        image.setImageResource(resId)
        image.contentDescription = faceValue.toString()
    }

    /**
     * Returns the resource ID for the current dice face.
     *
     * @return The resource ID of the current dice face.
     */
    fun getImageResourceId(): Int = getDrawableResId(faceValue)

    fun setDiceSize() {
        val sizeInPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            120f,
            contextParam.resources.displayMetrics
        ).toInt()
        image.layoutParams.width = sizeInPx
        image.layoutParams.height = sizeInPx
        image.scaleType = ImageView.ScaleType.CENTER_CROP
    }

    /**
     * Sets a click listener for the dice image.
     *
     * @param listener The click listener to attach.
     */
    fun setOnClickListener(listener: View.OnClickListener) {
        image.setOnClickListener(listener)
    }

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
