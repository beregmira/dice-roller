package ru.beregstudio.diceroller

import android.content.res.AssetManager
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

// Константы
const val SIX_SIDES: Int = 6 // Количество граней одного кубика
const val DEFAULT_NUMBER_OF_DICE: Int = 1

// Основной экран Activity
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layout: LinearLayout = findViewById(R.id.DiceLayout)
        val rollNumber: TextView = findViewById(R.id.diceNumber)
        val rollButton: Button = findViewById(R.id.diceButton)
        val seekDice: SeekBar = findViewById(R.id.seekBar)
        val diceRun = arrayListOf<Dice>()

        // Отрисовка кубика
        fun roller(numOfDice: Int) {
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
            getDiceRollSound().release()
            diceRun.clear()
            println("Stop debug")

        }
        roller(DEFAULT_NUMBER_OF_DICE)

        // Клик по кнопке "ROLL"
        rollButton.setOnClickListener {
            layout.removeAllViews()
            roller(seekDice.progress + 1)
        }

        // Событие изменения количества кубиков
        seekDice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                layout.removeAllViews()
                roller(seekBar.progress + 1)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    // Получение нового кубика
    private fun getDice(): Dice {
        return Dice(SIX_SIDES, this)
    }

    /**
     * Проигрывание звука броска кубика
     *
     * Функция возвращает объект [MediaPlayer] с отключенным лупингом и зазанным треком.
     * @return [MediaPlayer]
     */
    private fun getDiceRollSound(): MediaPlayer {
        val diceSound: MediaPlayer = MediaPlayer.create(this, R.raw.igralnaya_kost_upala)
        diceSound.isLooping = false
        return diceSound
    }
}