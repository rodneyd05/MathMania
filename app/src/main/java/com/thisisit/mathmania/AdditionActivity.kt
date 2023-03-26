package com.thisisit.mathmania

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.thisisit.mathmania.databinding.ActivityAdditionBinding
import java.util.*
import kotlin.random.Random

class AdditionActivity : AppCompatActivity() {

    private lateinit var additionBinding: ActivityAdditionBinding

    private var correctAnswer = 0
    private var userScore = 0
    private var userLife = 3

    private var value1 = 0
    private var value2 = 0
    private var value3 = 0
    private var value4 = 0

    //CountDownTimer class is an abstract class predefined in kotlin
    //creating an object in abstract classes is not allowed, we will encounter error once initialized
    private lateinit var timer: CountDownTimer
    private val startTimerInMillis: Long = 20_000
    var timeLeftInMillis: Long = startTimerInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        additionBinding = ActivityAdditionBinding.inflate(layoutInflater)
        setContentView(additionBinding.root)

        gameContinue()

        additionBinding.choiceA.setOnClickListener {
            pauseTimer()
            if (value1 == correctAnswer) {

                userScore += 10
                Toast.makeText(applicationContext, "Correct!", Toast.LENGTH_SHORT).show()
                additionBinding.textViewScore.text = userScore.toString()

                resetTimer()
                gameContinue()

            } else {
                userLife--
                additionBinding.textViewLife.text = userLife.toString()
                Toast.makeText(applicationContext, "Wrong", Toast.LENGTH_SHORT).show()

                resetTimer()

                if (userLife <= 0) {
                    gameOver()
                } else {
                    gameContinue()
                }
            }
        }

        additionBinding.choiceB.setOnClickListener {

            pauseTimer()

            if (value2 == correctAnswer) {
                userScore += 10
                Toast.makeText(applicationContext, "Correct!", Toast.LENGTH_SHORT).show()
                additionBinding.textViewScore.text = userScore.toString()

                resetTimer()
                gameContinue()
            } else {
                userLife--
                additionBinding.textViewLife.text = userLife.toString()
                Toast.makeText(applicationContext, "Wrong", Toast.LENGTH_SHORT).show()

                resetTimer()

                if (userLife <= 0) {
                    gameOver()
                } else {
                    gameContinue()
                }
            }
        }

        additionBinding.choiceC.setOnClickListener {

            pauseTimer()

            if (value3 == correctAnswer) {
                userScore += 10
                Toast.makeText(applicationContext, "Correct!", Toast.LENGTH_SHORT).show()
                additionBinding.textViewScore.text = userScore.toString()

                resetTimer()
                gameContinue()
            } else {
                userLife--
                additionBinding.textViewLife.text = userLife.toString()
                Toast.makeText(applicationContext, "Wrong", Toast.LENGTH_SHORT).show()

                resetTimer()

                if (userLife <= 0) {
                    gameOver()
                } else {
                    gameContinue()
                }
            }
        }

        additionBinding.choiceD.setOnClickListener {

            pauseTimer()

            if (value4 == correctAnswer) {
                userScore += 10
                Toast.makeText(applicationContext, "Correct!", Toast.LENGTH_SHORT).show()
                additionBinding.textViewScore.text = userScore.toString()

                resetTimer()
                gameContinue()
            } else {
                userLife--
                additionBinding.textViewLife.text = userLife.toString()
                Toast.makeText(applicationContext, "Wrong", Toast.LENGTH_SHORT).show()

                resetTimer()

                if (userLife <= 0) {
                    gameOver()
                } else {
                    gameContinue()
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun gameContinue() {
        val number1 = Random.nextInt(0, 100)
        val number2 = Random.nextInt(0, 100)

        correctAnswer = number1 + number2
        val firstOption = Random.nextInt(0, 200)
        val secondOption = Random.nextInt(0, 200)
        val thirdOption = Random.nextInt(0, 200)

        val valueList = listOf(correctAnswer, firstOption, secondOption, thirdOption)
        val randomValue = valueList.shuffled()

        value1 = randomValue[0]
        value2 = randomValue[1]
        value3 = randomValue[2]
        value4 = randomValue[3]


        additionBinding.textViewQuestion.text = "$number1 + $number2"
        additionBinding.choiceA.text = value1.toString()
        additionBinding.choiceB.text = value2.toString()
        additionBinding.choiceC.text = value3.toString()
        additionBinding.choiceD.text = value4.toString()

        startTimer()
    }

    private fun startTimer() {
        //error: cannot create an instance of an abstract class, use keyword object to represent anonymous class
        //need to implement 2 functions of the CountDownTimer class
        timer = object: CountDownTimer(timeLeftInMillis, 1000) {

            //p0 replaced by millisUntilFinished
            override fun onTick(millisUntilFinished: Long) {

                timeLeftInMillis = millisUntilFinished
                updateText()
            }

            override fun onFinish() {

                pauseTimer()
                resetTimer()
                updateText()

                userLife--
                additionBinding.textViewLife.text = userLife.toString()
                //additionBinding.textViewQuestion.text = "Time is up!"
                Toast.makeText(applicationContext, "Time is up!", Toast.LENGTH_LONG).show()

                if (userLife <= 0) {
                    gameOver()
                } else {
                    gameContinue()
                }
            }
        }.start()
    }

    fun updateText() {
        val remainingTime: Int = (timeLeftInMillis / 1000).toInt()
        additionBinding.textViewTime.text = String.format(Locale.getDefault(), "%02d", remainingTime)
    }

    fun pauseTimer() {
        timer.cancel()
    }

    fun resetTimer() {
        timeLeftInMillis = startTimerInMillis
        updateText()
    }

    fun gameOver() {
        Toast.makeText(applicationContext,"Game Over",Toast.LENGTH_LONG).show()
        val intent = Intent(this,ResultActivity::class.java)
        intent.putExtra("score",userScore)
        startActivity(intent)
        finish()
    }
}