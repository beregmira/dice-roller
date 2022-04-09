package ru.beregstudio.diceroller

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

const val SIX_SIDES: Int = 6
const val DEFAULT_NUMBER_OF_DICE: Int = 1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layout: LinearLayout = findViewById(R.id.DiceLayout)
        val rollNumber: TextView = findViewById(R.id.diceNumber)
        val rollButton: Button = findViewById(R.id.diceButton)
        val seekDice: SeekBar = findViewById(R.id.seekBar)

        roller(DEFAULT_NUMBER_OF_DICE, layout, rollNumber)

        // Клик по кнопке "ROLL"
        /**
         * Событие нажатия на кнопку броска  кубиков
         *
         * Обновляет основной экран вывода
         */
        rollButton.setOnClickListener {
            layout.removeAllViews()
            roller(seekDice.progress + 1, layout, rollNumber)
        }
        seekDice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            /**
             * Событие изменения количества кубиков
             *
             * Использование переключателя для выбора количества кубиков пользователем от 1 до 3
             * @param seekBar
             */
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                layout.removeAllViews()
                roller(seekBar.progress + 1, layout, rollNumber)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    /**
     * Отрисовка кубика на экране
     *
     * Функция принимает атрибуты количество кубиков [numOfDice], лейаут [layout] и подпись [rollNumber]
     * @param layout
     * @param numOfDice
     * @param rollNumber
     */
    private fun roller(numOfDice: Int, layout: LinearLayout, rollNumber: TextView) {
        val diceRun = arrayListOf<Dice>()
        var sum = 0
        for (i in 1..numOfDice) {
            diceRun.add(getDice())
        }
        println("Start debug")
        for (dice in diceRun) {
            layout.addView(dice.image)
            dice.setDiceSize()
            rollNumber.text = dice.diceRoll.toString()
            sum += dice.diceRoll
            rollNumber.text = sum.toString()
            println(dice.diceRoll.toString())
        }
        getDiceRollSound().start()
        diceRun.clear()
        println("Stop debug")

    }

    /**
     * Получение нового кубика
     *
     * Функция возвращает объект [Dice] c присвоенным колличеством сторон.
     * @return [Dice]
     */
    private fun getDice(): Dice {
        return Dice(SIX_SIDES, this)
    }

    /**
     * Проигрывание звука броска кубика
     *
     * Функция возвращает объект [MediaPlayer] c заданным треком.
     * @return [MediaPlayer]
     */
    private fun getDiceRollSound(): MediaPlayer {
        return MediaPlayer.create(this, R.raw.igralnaya_kost_upala)
    }
}