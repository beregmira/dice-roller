package ru.beregstudio.diceroller

import android.content.Context
import android.media.MediaPlayer
import android.widget.LinearLayout
import android.widget.TextView

class Roll(
    private val layout: LinearLayout, private val rollNumber: TextView,
    private val contextParam: Context
) {
    fun roller(numOfDice: Int) {
        val diceRun = arrayListOf<Dice>()
        var sum = 0
        val play = getDiceRollSound()
        play.start()
        for (i in 1..numOfDice) {
            diceRun.add(getDice())
        }
        println("Start debug")
        for (dice in diceRun) {
            layout.addView(dice.image)
            rollNumber.text = dice.diceRoll.toString()
            sum += dice.diceRoll
            rollNumber.text = sum.toString()
            dice.setDiceSize()
        }
        play.setOnCompletionListener {
            play.reset()
            play.release()
        }
        diceRun.clear()

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