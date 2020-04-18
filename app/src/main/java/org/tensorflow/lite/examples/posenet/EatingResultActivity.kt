package org.tensorflow.lite.examples.posenet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.posenet.MainActivity.Companion.pref

class EatingResultActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eating_result)

        val scoreLabel: TextView = findViewById(R.id.scoreLabel)
        val highScoreLabel: TextView = findViewById(R.id.HighScoreLabel)

        val score = intent.getIntExtra("Score", 0)
        val tmp = "$score"
        scoreLabel.text = tmp

//        pref = getSharedPreferences("Game_Data", Context.MODE_PRIVATE)
        val highScore = pref.getInt("MaxScore2", 0)

        if (score > highScore) {
            val tmp = "Highest Score : $score"
            highScoreLabel.text = tmp
            pref.edit()
                .putInt("MaxScore2", score)
                .apply()
        } else {
            val tmp = "Highest Score : $highScore"
            highScoreLabel.text = tmp
        }
    }

    fun tryAgain(view: View){
        startActivity(Intent(applicationContext, EatingActivity::class.java))
    }
}