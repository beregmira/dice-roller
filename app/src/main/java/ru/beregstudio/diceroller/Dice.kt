package ru.beregstudio.diceroller

import android.content.Context
import android.widget.ImageView
import kotlin.random.Random


class Dice(private val numSide: Int, private val diceSet: Int, contextParam: Context) {
    var diceRoll = getRandomDice()
    var image = ImageView(contextParam)

    private fun getDiceSetOneRandomImage(): Int = when (diceRoll) {
        1 -> R.drawable.set_1_dice_1
        2 -> R.drawable.set_1_dice_2
        3 -> R.drawable.set_1_dice_3
        4 -> R.drawable.set_1_dice_4
        5 -> R.drawable.set_1_dice_5
        else -> R.drawable.set_1_dice_6
    }

    private fun getDiceSetTwoRandomImage(): Int = when (diceRoll) {
        1 -> R.drawable.set_2_dice_1
        2 -> R.drawable.set_2_dice_2
        3 -> R.drawable.set_2_dice_3
        4 -> R.drawable.set_2_dice_4
        5 -> R.drawable.set_2_dice_5
        else -> R.drawable.set_2_dice_6
    }

    private fun getDiceSetThreeRandomImage(): Int = when (diceRoll) {
        1 -> R.drawable.set_3_dice_1
        2 -> R.drawable.set_3_dice_2
        3 -> R.drawable.set_3_dice_3
        4 -> R.drawable.set_3_dice_4
        5 -> R.drawable.set_3_dice_5
        else -> R.drawable.set_3_dice_6
    }

    init {
        setDiceImage()
    }

    fun setDiceImage() {
        when (diceSet) {
            1 -> image.setImageResource(getDiceSetOneRandomImage())
            2 -> image.setImageResource(getDiceSetTwoRandomImage())
            else -> image.setImageResource(getDiceSetThreeRandomImage())
        }
        image.contentDescription = diceRoll.toString()
    }

    fun setDiceSize() {
        image.layoutParams.width = 350
    }

    private fun getRandomDice(): Int {
        return Random.nextInt(numSide) + 1
    }
}