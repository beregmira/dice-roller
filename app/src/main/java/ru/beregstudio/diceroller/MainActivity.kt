package ru.beregstudio.diceroller

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

// Константы
const val SIX_SIDES: Int = 6 // Количество граней одного кубика
const val DEFAULT_NUMBER_OF_DICE: Int = 1

// Простое первое приложение на Kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val layout: LinearLayout = findViewById(R.id.DiceLayout)
        val rollNumber: TextView = findViewById(R.id.diceNumber)
        val rollButton: Button = findViewById(R.id.diceButton)
        val seekDice: SeekBar = findViewById(R.id.seekBar)
//        var seekBarText: TextView = findViewById(R.id.debugProgressBar)

//        seekBarText.text = (seekDice.progress+1).toString() + " dice"

        var diceRun = arrayListOf<Dice>()

        //Отрисовка кубика
        fun roller(numOfDice: Int) {
            var sum: Int = 0
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
//                println(dice.getRandomDice())
            }
            println("Stop debug")
            diceRun.clear()
        }
        roller(DEFAULT_NUMBER_OF_DICE)

//Клик по кнопке
        rollButton.setOnClickListener {
            layout.removeAllViews()
            roller(seekDice.progress + 1)
        }

//Событие изменения количества кубиков
        seekDice.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                layout.removeAllViews()
                roller(seekBar.progress + 1)
//                seekBarText.text = (seekDice.progress+1).toString() + " dices"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
    }

    //Получение нового кубика
    private fun getDice(): Dice {
        val dice = Dice(SIX_SIDES, this)
        dice.setDiceImage()
        return dice
    }

}