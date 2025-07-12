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
var diceset = 1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layout: LinearLayout = findViewById(R.id.DiceLayout)
        val rollNumber: TextView = findViewById(R.id.diceNumber)
        val rollButton: Button = findViewById(R.id.diceButton)
        val seekDice: SeekBar = findViewById(R.id.seekBar)
        val roll = Roll(layout, rollNumber, this)
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
        diceset = when (item.itemId) {
            R.id.red -> 1
            R.id.green -> 2
            else -> 3
        }
        return super.onOptionsItemSelected(item)
    }

}