package ru.beregstudio.diceroller

import android.content.Context
import android.media.MediaPlayer
import android.widget.LinearLayout
import android.widget.TextView

class Roll(private val layout: LinearLayout, private val rollNumber: TextView,
           private val contextParam: Context
) {
    fun roller(numOfDice: Int) {
        val diceRun = arrayListOf<Dice>()
        var sum = 0
        for (i in 1..numOfDice) {
            diceRun.add(getDice())
        }
        println("Start debug")
        for (dice in diceRun) {
            layout.addView(dice.image)
            rollNumber.text = dice.diceRoll.toString()
            sum += dice.diceRoll
            rollNumber.text = sum.toString()
            println(dice.diceRoll.toString())
            println("Image size:")
            dice.setDiceSize()
            println(dice.image.layoutParams.width.toString())
            println("Image size end")
        }
        getDiceRollSound().start()
        diceRun.clear()
        println("Stop debug")

    }
    private fun getDice(): Dice {
        return Dice(SIX_SIDES, diceset, contextParam)
    }
    /**
     * Проигрывание звука броска кубика
     *
     * Функция возвращает объект [MediaPlayer] c заданным треком.
     * @return [MediaPlayer]
     */
    private fun getDiceRollSound(): MediaPlayer {
        return MediaPlayer.create(contextParam, R.raw.igralnaya_kost_upala)
    }
}