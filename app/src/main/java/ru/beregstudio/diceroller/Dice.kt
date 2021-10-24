package ru.beregstudio.diceroller

import android.content.Context
import android.widget.ImageView
import kotlin.random.Random


class Dice(private val numSide: Int, context_param: Context) {
    var diceRoll = getRandomDice()
    var image = ImageView(context_param)

    private fun getDiceRandomImage(): Int {
        return when (diceRoll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    init {
        setDiceImage()
    }

    private fun setDiceImage() {
        image.setImageResource(getDiceRandomImage())
        image.contentDescription = diceRoll.toString()
    }

    fun setDiceSize() {
        image.layoutParams.height = 350
        image.layoutParams.width = 350
    }

    private fun getRandomDice(): Int {
        val diceRandom = Random.nextInt(numSide)
        return 1 + diceRandom
    }
}