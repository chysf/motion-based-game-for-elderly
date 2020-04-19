package org.tensorflow.lite.examples.posenet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import org.tensorflow.lite.examples.posenet.MainActivity.Companion.pref

class EatingResultActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eating_result)

        val scoreLabel: TextView = findViewById(R.id.scoreLabel)
        val highScoreLabel: TextView = findViewById(R.id.HighScoreLabel)

        val thisScore = intent.getIntExtra("Score", 0)
        val tmp = "$thisScore"
        scoreLabel.text = tmp

        val preferences = getSharedPreferences("Game_Data", Context.MODE_PRIVATE)
        val highScore = preferences.getInt("MaxScore2", 0)
        val temp = "$highScore"
        Toast.makeText(this, temp, Toast.LENGTH_SHORT).show()
        if (thisScore > highScore) {
            val tmp = "Highest Score : $thisScore"
            highScoreLabel.text = tmp
            preferences.edit()
                .putInt("MaxScore2", thisScore)
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