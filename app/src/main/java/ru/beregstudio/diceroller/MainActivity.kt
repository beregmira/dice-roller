package ru.beregstudio.diceroller

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val SIX_SIDES: Int = 6
const val DEFAULT_NUMBER_OF_DICE: Int = 1
var diceSet = 1

class MainActivity : AppCompatActivity() {
    private lateinit var roll: Roll
    private val layout: LinearLayout by lazy { findViewById(R.id.DiceLayout) }
    private val rollNumber: TextView by lazy { findViewById(R.id.diceNumber) }
    private val rollButton: Button by lazy { findViewById(R.id.diceButton) }
    private val seekDice: SeekBar by lazy { findViewById(R.id.seekBar) }

    private lateinit var shakeDetector: ShakeDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        roll = Roll(layout, rollNumber, this)
        roll.roller(DEFAULT_NUMBER_OF_DICE)
        
        rollButton.setOnClickListener {
            layout.removeAllViews()
            roll.roller(seekDice.progress + 1)
        }
        seekDice.setOnSeekBarChangeListener(/* l = */ object : SeekBar.OnSeekBarChangeListener {
            /**
             * Событие изменения количества кубиков
             *
             * Использование переключателя для выбора количества кубиков пользователем от 1 до 3
             * @param seekBar
             */
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                layout.removeAllViews()
                roll.roller(seekDice.progress + 1)
            }

            /**
             * Событие воспроизведение трека при старте
             *
             * Является обязательным для класса, но имплементация не требуется
             * @param seekBar
             */
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                /**
                 * Событие не применяется
                 */
            }

            /**
             * Событие при остановки воспроизведения
             *
             * Является обязательным для класса, но имплементация не требуется
             * @param seekBar
             */
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                /**
                 * Событие не применяется
                 */
            }
        })
        shakeDetector = ShakeDetector(this) {
            layout.removeAllViews()
            roll.roller(seekDice.progress + 1)
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
        roll.roller(numberOfDice)
    }
    /**
     * Событие при создании основного меню
     *
     * Является обязательным при добавлении основного бокового меню в приложении
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater.inflate(R.menu.main, menu)
        println(inflater.toString())
        return super.onCreateOptionsMenu(menu)
    }
    /**
     * Событие при выборе элемента основного бокового меню
     *
     * При выборе скина кубиков фиксируем значение в глобальной переменной diceset
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        diceSet = when (item.itemId) {
            R.id.red -> 1
            R.id.green -> 2
            else -> 3
        }
        resetDice(seekDice.progress + 1)
        return super.onOptionsItemSelected(item)
    }

}