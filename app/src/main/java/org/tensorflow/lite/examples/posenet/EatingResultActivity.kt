package org.tensorflow.lite.examples.posenet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.posenet.EatingActivity.Companion.maxScoreC
import org.tensorflow.lite.examples.posenet.EatingActivity.Companion.score2

class EatingResultActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eating_result)

        val scoreLabel: TextView = findViewById(R.id.scoreLabel)
        val highScoreLabel: TextView = findViewById(R.id.HighScoreLabel)

        if(score2 > maxScoreC) {
            maxScoreC = score2
        }

        val tmp = "$score2"
        scoreLabel.text = tmp
        score2 = 0

        val tmp1 = "Highest Score : $maxScoreC"
        highScoreLabel.text = tmp1
    }

    fun tryAgain(view: View){
        startActivity(Intent(this, EatingActivity::class.java))
    }
    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}