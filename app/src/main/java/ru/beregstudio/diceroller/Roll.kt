package ru.beregstudio.diceroller

import android.animation.ObjectAnimator
import android.content.Context
import android.media.MediaPlayer
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import android.animation.AnimatorListenerAdapter
import android.animation.Animator

/**
 * Represents a dice roll operation.
 *
 * @param layout The layout where dice images will be added.
 * @param rollNumber The TextView that displays the sum of dice rolls.
 * @param contextParam The context used for creating dice and media player.
 */

class Roll(
    private val layout: LinearLayout, private val rollNumber: TextView,
    private val contextParam: Context
) {
    fun roller(numOfDice: Int) {
        val diceRun = arrayListOf<Dice>()
        var sum = 0
        val play = getDiceRollSound()
        play.start()
        (1..numOfDice).forEach { _ ->
            diceRun.add(getDice())
        }
        for (dice in diceRun) {
            layout.addView(dice.image)
            rollNumber.text = dice.diceRoll.toString()
            sum += dice.diceRoll
            rollNumber.text = sum.toString()
            dice.setDiceSize()

            val duration = (300..600).random().toLong()
            val endRotation = (360..720).random().toFloat()

            ObjectAnimator.ofFloat(dice.image, "rotation", 0f, endRotation).apply {
                this.duration = duration
                interpolator = AccelerateDecelerateInterpolator()
                repeatCount = 1
                repeatMode = ObjectAnimator.REVERSE
                this.startDelay = 0

                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        dice.setDiceImage()
                    }
                })

                start()

            }
        }
        play.setOnCompletionListener {
            play.reset()
            play.release()
        }
        diceRun.clear()

    }

    private fun getDice(): Dice {
        return Dice(SIX_SIDES, diceSet, contextParam)
    }

    /**
     * Plays dice roll sound.
     *
     * Creates and returns a [MediaPlayer] instance with the specified sound track.
     *
     * @return [MediaPlayer] instance configured with the dice roll sound
     */

    private fun getDiceRollSound(): MediaPlayer {
        return MediaPlayer.create(contextParam, R.raw.igralnaya_kost_upala)
    }
}