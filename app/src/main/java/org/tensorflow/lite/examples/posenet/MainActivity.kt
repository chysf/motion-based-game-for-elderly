package org.tensorflow.lite.examples.posenet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.posenet.PosenetActivity.Companion.gameID


class MainActivity : AppCompatActivity() {

    private lateinit var motionGameBtn: Button
    private lateinit var eatingGameBtn: Button
    private lateinit var memoryGameBtn: Button
    private lateinit var testBtn: Button

    companion object{
        var user = "admin"
        lateinit var pref: SharedPreferences
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pref = getSharedPreferences("test", Context.MODE_PRIVATE)
        pref.edit()
            .putString("USER", user)
            .putInt("MaxScore1", 0)
            .putInt("MaxScore2", 0)
            .putInt("MaxScore3", 0)
            .putInt("LemonSum", 0)
            .putInt("GrapeSum", 0)
            .apply()

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

    override fun onStart() {
        super.onStart()
        enableButton()
    }
    override fun onResume() {
        super.onResume()
        enableButton()
    }
    override fun onPause() {
        super.onPause()
        disableButton()
    }
    override fun onStop() {
        super.onStop()
        disableButton()
    }

    //override fun onBackPressed() {
    //super.onBackPressed()

    //val builder = AlertDialog.Builder(this)

    //builder.setMessage("Are you sure to Exit?")
    //builder.setCancelable(true)

    // builder.setNegativeButton("No", DialogInterface.OnClickListener{ dialogInterface, i ->

    // dialogInterface.cancel()
    //  })
    // builder.setPositiveButton("Exit", DialogInterface.OnClickListener{ dialogInterface, i ->

    //    finish()
    //      System.exit(0)
    //  })

    //  val alertDialog = builder.create()
    //  alertDialog.show()

    //}

}