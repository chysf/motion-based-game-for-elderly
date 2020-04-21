package org.tensorflow.lite.examples.posenet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.posenet.EatingActivity.Companion.grapeC
import org.tensorflow.lite.examples.posenet.EatingActivity.Companion.lemonC
import org.tensorflow.lite.examples.posenet.EatingActivity.Companion.maxScoreC
import org.tensorflow.lite.examples.posenet.PosenetActivity.Companion.gameID


class MainActivity : AppCompatActivity() {

    private lateinit var motionGameBtn: Button
    private lateinit var eatingGameBtn: Button
    private lateinit var memoryGameBtn: Button
    private lateinit var testBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = getSharedPreferences("Game_Data", MODE_PRIVATE)
        if(!pref.contains("MaxScore2")) {
            pref.edit()
                .putInt("MaxScore1", 0)
                .putInt("MaxScore2", 0)
                .putInt("MaxScore3", 0)
                .putInt("LemonSum", 0)
                .putInt("GrapeSum", 0)
                .putInt("VegSum", 0)
                .apply()
        }

        val clickListener = View.OnClickListener { view ->
            when (view.id) {
                R.id.motionGameBtn -> {
                    val intent = Intent(this, CameraActivity::class.java)
                    gameID = 1
                    startActivity(intent)
                }
                R.id.eatingGameBtn -> {
                    val intent = Intent(this, EatingActivity::class.java)
                    gameID = 2
                    startActivity(intent)
                }
                R.id.memoryGameBtn -> {
                    val intent = Intent(this, MemoryActivity::class.java)
                    gameID = 3
                    startActivity(intent)
                }
                R.id.testBtn -> {
                    val intent = Intent(this, Achievements::class.java)
                    gameID = 4
                    startActivity(intent)
                }
            }
        }
        motionGameBtn = findViewById(R.id.motionGameBtn)
        motionGameBtn.setOnClickListener(clickListener)
        eatingGameBtn = findViewById(R.id.eatingGameBtn)
        eatingGameBtn.setOnClickListener(clickListener)
        memoryGameBtn = findViewById(R.id.memoryGameBtn)
        memoryGameBtn.setOnClickListener(clickListener)
        testBtn = findViewById(R.id.testBtn)
        testBtn.setOnClickListener(clickListener)

    }
    private fun enableButton(){
        motionGameBtn.isEnabled = true
        eatingGameBtn.isEnabled = true
        memoryGameBtn.isEnabled = true
        testBtn.isEnabled = true
    }
    private fun disableButton(){
        motionGameBtn.isEnabled = false
        eatingGameBtn.isEnabled = false
        memoryGameBtn.isEnabled = false
        testBtn.isEnabled = false
    }
    private fun save(){
        val preff = getSharedPreferences("Game_Data", MODE_PRIVATE)
        var tmpL = preff.getInt("LemonSum", 0)
        tmpL += lemonC
        lemonC = 0
        var tmpG = preff.getInt("GrapeSum", 0)
        tmpG += grapeC
        grapeC = 0
        var tmpS = if(maxScoreC == 0) preff.getInt("MaxScore2", 0) //start
        else maxScoreC //resume

        preff.edit()
            .putInt("LemonSum", tmpL)
            .putInt("GrapeSum", tmpG)
            .putInt("MaxScore2", tmpS)
            .apply()
    }
    override fun onStart() {
        super.onStart()
        enableButton()
        save()
    }
    override fun onResume() {
        super.onResume()
        enableButton()
        save()
    }
    override fun onPause() {
        super.onPause()
        disableButton()
    }
    override fun onStop() {
        super.onStop()
        disableButton()
    }

    override fun onBackPressed() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

}