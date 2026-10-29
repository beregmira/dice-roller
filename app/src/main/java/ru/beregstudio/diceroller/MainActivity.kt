package ru.beregstudio.diceroller

import android.content.Intent
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView
import ru.beregstudio.diceroller.domain.DiceRoller


const val DEFAULT_NUMBER_OF_DICE: Int = 1
var diceSet = 1

/**
 * Main activity for the Dice Roller application.
 *
 * This class handles the main UI interactions including:
 * - Rolling dice with button click or device shake
 * - Adjusting number of dice with seek bar
 * - Changing dice skin through options menu
 * - Managing shake detection lifecycle
 *
 * @property roll The Roll instance that handles dice rolling logic
 * @property layout The LinearLayout containing dice views
 * @property rollNumber TextView showing the total rolled number
 * @property rollButton Button to trigger dice roll
 * @property seekDice SeekBar for selecting number of dice
 * @property shakeDetector Detects device shake events to trigger dice rolls
 */

class MainActivity : AppCompatActivity() {
    private lateinit var roll: Roll
    private val layout: LinearLayout by lazy { findViewById(R.id.DiceLayout) }
    private val rollNumber: TextView by lazy { findViewById(R.id.diceNumber) }
    private val rollButton: Button by lazy { findViewById(R.id.diceButton) }
    private val seekDice: SeekBar by lazy { findViewById(R.id.seekBar) }
    private val resultCard: MaterialCardView by lazy { findViewById(R.id.resultCard) }

    private lateinit var shakeDetector: ShakeDetector
    private val diceRoller = DiceRoller()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        diceSet = getSharedPreferences("app_prefs", MODE_PRIVATE).getInt("dice_set", 1)
        roll = Roll(layout, rollNumber, this, diceRoller)
        performRoll(DEFAULT_NUMBER_OF_DICE)

        rollButton.setOnClickListener {
            performRoll(seekDice.progress + 1)
        }

        seekDice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                updateDicePreview(progress + 1)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        shakeDetector = ShakeDetector(this) {
            performRoll(seekDice.progress + 1)
        }
    }

    private fun performRoll(numberOfDice: Int) {
        if (!rollButton.isEnabled) return

        // Подготовка к броску: скрываем старый результат (всю карточку!)
        resultCard.visibility = View.INVISIBLE
        resultCard.scaleX = 0.5f
        resultCard.scaleY = 0.5f
        resultCard.alpha = 0f

        layout.removeAllViews()
        roll.roller(
            numberOfDice,
            diceSet,
            onStart = {
                rollButton.isEnabled = false
            },
            onFinish = {
                rollButton.isEnabled = true
                window.decorView.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                animateResult() // Запускаем магию анимации для карточки!
            }
        )
    }

    private fun animateResult() {
        resultCard.visibility = View.VISIBLE
        resultCard.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .alpha(1.0f)
            .setDuration(500)
            .setInterpolator(OvershootInterpolator())
            .start()
    }

    private var lastPreviewCount = -1

    private fun updateDicePreview(count: Int) {
        if (count == lastPreviewCount) return
        lastPreviewCount = count

        layout.removeAllViews()
        repeat(count) {
            val dice = Dice(1, diceSet, this)
            dice.setOnClickListener {
                val intent = Intent(this, DiceDetailActivity::class.java).apply {
                    putExtra("EXTRA_IMAGE_RES_ID", dice.getImageResourceId())
                }
                startActivity(intent)
            }
            layout.addView(dice.image)
            dice.setDiceSize()
        }
    }

    override fun onResume() {
        super.onResume()
        shakeDetector.start()
    }

    override fun onPause() {
        super.onPause()
        shakeDetector.stop()
    }

    private fun resetDice(numberOfDice: Int) {
        layout.removeAllViews()
        roll.roller(numberOfDice, diceSet, onStart = {}, onFinish = {})
    }

    /**
     * Handles the creation of main option's menu.
     * This is required when implementing main side menu in the application.
     *
     * @param menu The options menu in which items are placed.
     * @return Boolean indicating whether the menu was successfully created.
     */

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Handles selection of items in the main side menu.
     * When a dice skin is selected, stores the selected value in the global variable [diceSet]
     * and resets the dice with the current progress value.
     *
     * @param item The menu item to select
     * @return Boolean indicating whether the selection was handled
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        diceSet = when (item.itemId) {
            R.id.red -> 1
            R.id.green -> 2
            R.id.dark -> 3
            R.id.sakura -> 4
            R.id.snakes -> 5
            else -> 6
        }
        saveDiceSet(diceSet)
        resetDice(seekDice.progress + 1)
        return super.onOptionsItemSelected(item)
    }

    /**
     * Saves the selected dice set to shared preferences.
     *
     * @param set The set of dice to save.
     */

    private fun saveDiceSet(set: Int) {
        val sharedPref = getSharedPreferences("app_prefs", MODE_PRIVATE)
        sharedPref.edit().apply {
            putInt("dice_set", set)
            apply()
        }
    }

}