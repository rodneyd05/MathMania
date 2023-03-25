package com.thisisit.mathmania

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

    //CountDownTimer class is an abstract class pre defined in kotlin
    //creating an object in abstract classes is not allowed, we will encounter error once initialized
    private lateinit var timer: CountDownTimer
    private val startTimerInMillis: Long = 60_000
    var timeLeftInMillis: Long = startTimerInMillis

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        additionBinding = ActivityAdditionBinding.inflate(layoutInflater)
        setContentView(additionBinding.root)

//        //Change the title in the action bar
//        supportActionBar!!.title = "Addition"

        gameContinue()

        additionBinding.buttonOk.setOnClickListener {

            val input = additionBinding.editTextAnswer.text.toString()

            if (input == "") {
                Toast.makeText(applicationContext, "Please write an answer or click the next button", Toast.LENGTH_SHORT).show()
            } else {

                pauseTimer()

                val userAnswer = input.toInt()

                if (userAnswer == correctAnswer) {
                    userScore += 10
                    additionBinding.textViewQuestion.text = "Congratulations, your answer is correct"
                    additionBinding.textViewScore.text = userScore.toString()

                } else {
                    userLife--
                    additionBinding.textViewQuestion.text = "Sorry, your answer is wrong"
                    additionBinding.textViewLife.text = userLife.toString()

                }
            }

        }

        additionBinding.buttonNext.setOnClickListener {

            pauseTimer()
            resetTimer()

            gameContinue()
            additionBinding.editTextAnswer.setText("")

            if (userLife <= 0) {

                Toast.makeText(applicationContext,"Game Over",Toast.LENGTH_LONG).show()
                val intent = Intent(this,ResultActivity::class.java)
                intent.putExtra("score",userScore)
                startActivity(intent)
                finish()
            } else {
                gameContinue()
            }

        }
    }

    private fun gameContinue() {
        val number1 = Random.nextInt(0, 100)
        val number2 = Random.nextInt(0, 100)

        additionBinding.textViewQuestion.text = "$number1 + $number2"

        correctAnswer = number1 + number2

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
                additionBinding.textViewQuestion.text = "Sorry, Time is up!"
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
}