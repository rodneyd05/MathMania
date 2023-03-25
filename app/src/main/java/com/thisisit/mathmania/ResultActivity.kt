package com.thisisit.mathmania

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisisit.mathmania.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var resultBinding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        resultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(resultBinding.root)

        //keyword is score
        val score = intent.getIntExtra("score",0)
        resultBinding.textViewResult.text = "Your score: $score"

        resultBinding.buttonAgain.setOnClickListener {

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)

            //when we pass using intent from one activity to another activity we should close the older activity
            finish()
        }

        resultBinding.buttonExit.setOnClickListener {

            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}