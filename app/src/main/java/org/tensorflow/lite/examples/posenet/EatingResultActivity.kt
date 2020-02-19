package org.tensorflow.lite.examples.posenet

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EatingResultActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eating_result)

        val scoreLabel: TextView = findViewById(R.id.scoreLabel)
        val highScoreLabel: TextView = findViewById(R.id.HighScoreLabel)

        val score = intent.getIntExtra("Score", 0)
        val tmp = "$score "
        scoreLabel.text = tmp

        val settings = getSharedPreferences("Game_Data", Context.MODE_PRIVATE)
        val highScore = settings.getInt("High_Score", 0)

        if (score > highScore) {
            val tmp = "High Score : $score"
            highScoreLabel.text = tmp
            val editor = settings.edit()
            editor.putInt("High_Score", score)
            editor.apply()
        } else {
            val tmp = "High Score : $highScore"
            highScoreLabel.text = tmp
        }
    }
}