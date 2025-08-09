package ru.beregstudio.diceroller

import android.content.Context
import android.widget.ImageView
import kotlin.random.Random


class Dice(private val numSide: Int, private val diceSet: Int, contextParam: Context) {
    val diceRoll = getRandomDice()
    val image = ImageView(contextParam)

    private val diceResources = mapOf(
        1 to listOf(
            R.drawable.set_1_dice_1,
            R.drawable.set_1_dice_2,
            R.drawable.set_1_dice_3,
            R.drawable.set_1_dice_4,
            R.drawable.set_1_dice_5,
            R.drawable.set_1_dice_6
        ),
        2 to listOf(
            R.drawable.set_2_dice_1,
            R.drawable.set_2_dice_2,
            R.drawable.set_2_dice_3,
            R.drawable.set_2_dice_4,
            R.drawable.set_2_dice_5,
            R.drawable.set_2_dice_6
        ),
        3 to listOf(
            R.drawable.set_3_dice_1,
            R.drawable.set_3_dice_2,
            R.drawable.set_3_dice_3,
            R.drawable.set_3_dice_4,
            R.drawable.set_3_dice_5,
            R.drawable.set_3_dice_6
        )
    )

    init {
        setDiceImage()
    }

    fun setDiceImage() {
        val resources = diceResources[diceSet] ?: return
        image.setImageResource(resources[diceRoll - 1])
        image.contentDescription = diceRoll.toString()
    }

    fun setDiceSize() {
        image.layoutParams.width = 350
    }

    private fun getRandomDice(): Int {
        return Random.nextInt(numSide) + 1
    }
}