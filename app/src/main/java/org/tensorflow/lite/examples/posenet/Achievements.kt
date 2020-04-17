package org.tensorflow.lite.examples.posenet

import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Achievements: AppCompatActivity() {
    private lateinit var maxScore1: TextView
    private lateinit var star1_b: ImageView
    private lateinit var star2_b: ImageView
    private lateinit var star3_b: ImageView
    private lateinit var star1: ImageView
    private lateinit var star2: ImageView
    private lateinit var star3: ImageView

    private lateinit var maxScore2: TextView
    private lateinit var fruit1_b: ImageView
    private lateinit var fruit2_b: ImageView
    private lateinit var fruit3_b: ImageView
    private lateinit var fruit1: ImageView
    private lateinit var fruit2: ImageView
    private lateinit var fruit3: ImageView


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
            star1_b.visibility = INVISIBLE
            star1.visibility = VISIBLE
        }
        if (score1 >= STAR2) {
            star2_b.visibility = INVISIBLE
            star2.visibility = VISIBLE
        }
        if (score1 >= STAR3) {
            star3_b.visibility = INVISIBLE
            star3.visibility = VISIBLE
        }
    }
    private fun init(){
        //game1
        maxScore1 = findViewById(R.id.max_score1)
        star1_b = findViewById(R.id.star1_outline)
        star2_b = findViewById(R.id.star2_outline)
        star3_b = findViewById(R.id.star3_outline)
        star1 = findViewById(R.id.star1_color)
        star2 = findViewById(R.id.star2_color)
        star3 = findViewById(R.id.star3_color)
        //game2

    }
}