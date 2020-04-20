package org.tensorflow.lite.examples.posenet

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.posenet.EatingActivity.Companion.grapeC
import org.tensorflow.lite.examples.posenet.EatingActivity.Companion.lemonC
import org.tensorflow.lite.examples.posenet.EatingActivity.Companion.maxScoreC

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

    private lateinit var lemonScore: TextView
    private lateinit var lemon1_b: ImageView
    private lateinit var lemon2_b: ImageView
    private lateinit var lemon3_b: ImageView
    private lateinit var lemon1: ImageView
    private lateinit var lemon2: ImageView
    private lateinit var lemon3: ImageView

    private lateinit var grapeScore: TextView
    private lateinit var grape1_b: ImageView
    private lateinit var grape2_b: ImageView
    private lateinit var grape3_b: ImageView
    private lateinit var grape1: ImageView
    private lateinit var grape2: ImageView
    private lateinit var grape3: ImageView

    private lateinit var maxScore3: TextView
    private lateinit var harvest1_b: ImageView
    private lateinit var harvest2_b: ImageView
    private lateinit var harvest3_b: ImageView
    private lateinit var harvest1: ImageView
    private lateinit var harvest2: ImageView
    private lateinit var harvest3: ImageView

    private lateinit var rstBtn: Button

    //score to unlock achievement
    private val STAR1 = 5
    private val STAR2 = 15
    private val STAR3 = 20

    private val FRUIT1 = 50
    private val FRUIT2 = 100
    private val FRUIT3 = 200

    private val LEMON1 = 5
    private val LEMON2 = 15
    private val LEMON3 = 20

    private val GRAPE1 = 5
    private val GRAPE2 = 15
    private val GRAPE3 = 20
//    mismatch
    private val HARVEST1 = 20
    private val HARVEST2 = 10
    private val HARVEST3 = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_achievement)
        init()
        refresh()
        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.rst_btn -> {
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Are you sure to reset all data?")
                    builder.setCancelable(true)
                    builder.setNegativeButton("No") { dialogInterface, _ ->
                        dialogInterface.cancel()
                    }
                    builder.setPositiveButton("Yes") { _, _ ->
                        getSharedPreferences("Game_Data", Context.MODE_PRIVATE).edit()
                            .putInt("MaxScore1", 0)
                            .putInt("MaxScore2", 0)
                            .putInt("MinScore3", 50)
                            .putInt("LemonSum", 0)
                            .putInt("GrapeSum", 0)
                            .apply()
                        refresh()
                        Toast.makeText(this, "reset", Toast.LENGTH_SHORT).show()
                    }
                    builder.create().show()
                }
            }
        }
        rstBtn.setOnClickListener(clickListener)
    }

    override fun onRestart() {
        super.onRestart()
        refresh()
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh(){
//        val preff = getSharedPreferences("Game_Data", Context.MODE_PRIVATE)
//        var tmpL = preff.getInt("LemonSum", 0)
//        tmpL += lemonC
//        lemonC = 0
//        var tmpG = preff.getInt("GrapeSum", 0)
//        tmpG += grapeC
//        grapeC = 0
//
//        preff.edit()
//            .putInt("LemonSum", tmpL)
//            .putInt("GrapeSum", tmpG)
//            .putInt("MaxScore2", maxScoreC)
//            .apply()
        val score1 = getSharedPreferences("Game_Data", Context.MODE_PRIVATE).getInt("MaxScore1", 0)
        maxScore1.text = "Maximum Score: $score1"
        if (score1 >= STAR1) {
            star1_b.visibility = INVISIBLE
            star1.visibility = VISIBLE
        }
        else{
            star1.visibility = INVISIBLE
            star2.visibility = INVISIBLE
            star3.visibility = INVISIBLE
            star1_b.visibility = VISIBLE
            star2_b.visibility = VISIBLE
            star3_b.visibility = VISIBLE
        }
        if (score1 >= STAR2) {
            star2_b.visibility = INVISIBLE
            star2.visibility = VISIBLE
        }
        if (score1 >= STAR3) {
            star3_b.visibility = INVISIBLE
            star3.visibility = VISIBLE
        }

        val score2 = getSharedPreferences("Game_Data", Context.MODE_PRIVATE).getInt("MaxScore2", 0)
        maxScore2.text = "Maximum Score: $score2"
        if (score2 >= FRUIT1) {
            fruit1_b.visibility = INVISIBLE
            fruit1.visibility = VISIBLE
        }else{
            fruit1.visibility = INVISIBLE
            fruit2.visibility = INVISIBLE
            fruit3.visibility = INVISIBLE
            fruit1_b.visibility = VISIBLE
            fruit2_b.visibility = VISIBLE
            fruit3_b.visibility = VISIBLE
        }
        if (score2 >= FRUIT2) {
            fruit2_b.visibility = INVISIBLE
            fruit2.visibility = VISIBLE
        }
        if (score2 >= FRUIT3) {
            fruit3_b.visibility = INVISIBLE
            fruit3.visibility = VISIBLE
        }

        val lemonSum = getSharedPreferences("Game_Data", Context.MODE_PRIVATE).getInt("LemonSum", 0)
        lemonScore.text = "Accumulated Lemons: $lemonSum"
        if (lemonSum >= LEMON1) {
            lemon1_b.visibility = INVISIBLE
            lemon1.visibility = VISIBLE
        }else{
            lemon1.visibility = INVISIBLE
            lemon2.visibility = INVISIBLE
            lemon3.visibility = INVISIBLE
            lemon1_b.visibility = VISIBLE
            lemon2_b.visibility = VISIBLE
            lemon3_b.visibility = VISIBLE
        }
        if (lemonSum >= LEMON2) {
            lemon2_b.visibility = INVISIBLE
            lemon2.visibility = VISIBLE
        }
        if (lemonSum >= LEMON3) {
            lemon3_b.visibility = INVISIBLE
            lemon3.visibility = VISIBLE
        }

        val grapeSum = getSharedPreferences("Game_Data", Context.MODE_PRIVATE).getInt("GrapeSum", 0)
        grapeScore.text = "Accumulated Grapes: $grapeSum"
        if (grapeSum >= GRAPE1) {
            grape1_b.visibility = INVISIBLE
            grape1.visibility = VISIBLE
        }else{
            grape1.visibility = INVISIBLE
            grape2.visibility = INVISIBLE
            grape3.visibility = INVISIBLE
            grape1_b.visibility = VISIBLE
            grape2_b.visibility = VISIBLE
            grape3_b.visibility = VISIBLE
        }
        if (grapeSum >= GRAPE2) {
            grape2_b.visibility = INVISIBLE
            grape2.visibility = VISIBLE
        }
        if (grapeSum >= GRAPE3) {
            grape3_b.visibility = INVISIBLE
            grape3.visibility = VISIBLE
        }

        val score3 = getSharedPreferences("Game_Data", Context.MODE_PRIVATE).getInt("MinScore3", 50)
        if(score3 == 50) maxScore3.text = "N/A"
        else maxScore3.text = "Minimum Mismatches: $score3"
        if (score3 <= HARVEST1) {
            harvest1_b.visibility = INVISIBLE
            harvest1.visibility = VISIBLE
        }else{
            harvest1.visibility = INVISIBLE
            harvest2.visibility = INVISIBLE
            harvest3.visibility = INVISIBLE
            harvest1_b.visibility = VISIBLE
            harvest2_b.visibility = VISIBLE
            harvest3_b.visibility = VISIBLE
        }
        if (score3 <= HARVEST2) {
            harvest2_b.visibility = INVISIBLE
            harvest2.visibility = VISIBLE
        }
        if (score3 <= HARVEST3) {
            harvest3_b.visibility = INVISIBLE
            harvest3.visibility = VISIBLE
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
        maxScore2 = findViewById(R.id.max_score2)
        fruit1_b = findViewById(R.id.fruit1_outline)
        fruit2_b = findViewById(R.id.fruit2_outline)
        fruit3_b = findViewById(R.id.fruit3_outline)
        fruit1 = findViewById(R.id.fruit1_color)
        fruit2 = findViewById(R.id.fruit2_color)
        fruit3 = findViewById(R.id.fruit3_color)

        lemonScore = findViewById(R.id.lemon_score)
        lemon1_b = findViewById(R.id.lemon1_outline)
        lemon2_b = findViewById(R.id.lemon2_outline)
        lemon3_b = findViewById(R.id.lemon3_outline)
        lemon1 = findViewById(R.id.lemon1_color)
        lemon2 = findViewById(R.id.lemon2_color)
        lemon3 = findViewById(R.id.lemon3_color)

        grapeScore = findViewById(R.id.grape_score)
        grape1_b = findViewById(R.id.grape1_outline)
        grape2_b = findViewById(R.id.grape2_outline)
        grape3_b = findViewById(R.id.grape3_outline)
        grape1 = findViewById(R.id.grape1_color)
        grape2 = findViewById(R.id.grape2_color)
        grape3 = findViewById(R.id.grape3_color)

        //game3
        maxScore3 = findViewById(R.id.max_score3)
        harvest1_b = findViewById(R.id.harvest1_outline)
        harvest2_b = findViewById(R.id.harvest2_outline)
        harvest3_b = findViewById(R.id.harvest3_outline)
        harvest1 = findViewById(R.id.harvest1_color)
        harvest2 = findViewById(R.id.harvest2_color)
        harvest3 = findViewById(R.id.harvest3_color)

        rstBtn = findViewById(R.id.rst_btn)
    }
}