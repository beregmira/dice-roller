package ru.beregstudio.diceroller

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
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
 * @param diceRoller The domain component for rolling dice.
 */
class Roll(
    private val layout: LinearLayout,
    private val rollNumber: TextView,
    private val contextParam: Context,
    private val diceRoller: ru.beregstudio.diceroller.domain.DiceRoller,
) {
    fun roller(numOfDice: Int, diceSet: Int, onStart: () -> Unit, onFinish: () -> Unit) {
        val results = List(numOfDice) { diceRoller.roll(6) }
        val sum = results.sum()

        onStart()

        val play = getDiceRollSound()
        play.start()

        var completedAnimations = 0

        for (i in 0 until numOfDice) {
            val result = results[i]
            val dice = Dice(result, diceSet, contextParam)
            
            layout.addView(dice.image)
            dice.setDiceSize()
            
            // Add click listener to show detail view
            dice.image.setOnClickListener {
                val intent = Intent(contextParam, DiceDetailActivity::class.java).apply {
                    putExtra("EXTRA_IMAGE_RES_ID", dice.getImageResourceId())
                }
                contextParam.startActivity(intent)
            }

            animateDice(dice) {
                completedAnimations++
                if (completedAnimations == numOfDice) {
                    rollNumber.text = sum.toString()
                    play.setOnCompletionListener { it.release() }
                    onFinish()
                }
            }
        }
    }

    private fun animateDice(dice: Dice, onFinished: () -> Unit) {
        val duration = (300..600).random().toLong()
        val endRotation = (360..720).random().toFloat()

        ObjectAnimator.ofFloat(dice.image, "rotation", 0f, endRotation).apply {
            this.duration = duration
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = 1
            repeatMode = ObjectAnimator.REVERSE
            startDelay = 0
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    dice.setDiceImage()
                    onFinished()
                }
            })
            start()
        }
    }

    private fun getDiceRollSound(): MediaPlayer {
        return MediaPlayer.create(contextParam, R.raw.igralnaya_kost_upala)
    }
}
