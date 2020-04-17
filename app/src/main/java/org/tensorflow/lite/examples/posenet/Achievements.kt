package org.tensorflow.lite.examples.posenet

import android.content.Context
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.posenet.MainActivity.Companion.pref

class Achievements: AppCompatActivity() {
    private lateinit var maxScore1: TextView
    private lateinit var medal1: ImageView
    private lateinit var medal2: ImageView
    private lateinit var medal3: ImageView
    private lateinit var star1: ImageView
    private lateinit var star2: ImageView
    private lateinit var star3: ImageView

    //score to unlock achievement
    private val STAR1 = 5
    private val STAR2 = 15
    private val STAR3 = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)
        init()
        val score1 = getSharedPreferences("test", MODE_PRIVATE).getInt("MaxScore1", 0)
        maxScore1.text = "Maximum Score: $score1"
        if (score1 >= STAR1) {
            medal1.visibility = INVISIBLE
            star1.visibility = VISIBLE
        }
        if (score1 >= STAR2) {
            medal2.visibility = INVISIBLE
            star2.visibility = VISIBLE
        }
        if (score1 >= STAR3) {
            medal3.visibility = INVISIBLE
            star3.visibility = VISIBLE
        }
    }
    private fun init(){
        maxScore1 = findViewById(R.id.max_score1)
        medal1 = findViewById(R.id.medal1)
        medal2 = findViewById(R.id.medal2)
        medal3 = findViewById(R.id.medal3)
        star1 = findViewById(R.id.star1)
        star2 = findViewById(R.id.star2)
        star3 = findViewById(R.id.star3)
    }
}