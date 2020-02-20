package org.tensorflow.lite.examples.posenet

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.random.Random

class EatingActivity: AppCompatActivity() {

    private lateinit var scoreLabel: TextView
    private lateinit var startLabel: TextView
    private lateinit var mouth: ImageView
    private lateinit var lemon: ImageView
    private lateinit var grape: ImageView
    private lateinit var shit: ImageView

    private var frameheight = 0
    private var boxsize = 0
    private var screenWidth = 0
    private var screenHeight = 0

    private var boxY = 0
    private var lemonX = 0
    private var lemonY = 0
    private var grapeX = 0
    private var grapeY = 0
    private var shitX = 0
    private var shitY = 0

    private var score = 0

    private var handler = Handler()
    private var timer = Timer()

    private var action_flg = false
    private var start_flg = false

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eating)

        scoreLabel = findViewById(R.id.scoreLabel)
        startLabel = findViewById(R.id.startLabel)
        mouth = findViewById(R.id.mouth)
        lemon = findViewById(R.id.lemon)
        grape = findViewById(R.id.grape)
        shit = findViewById(R.id.shit)


        var disp = windowManager.defaultDisplay
        var size = Point(0,0)
        disp.getSize(size)

        screenWidth = size.x
        screenHeight = size.y

        lemon.x = -80.0f
        lemon.y = -80.0f
        grape.x = -80.0f
        grape.y = -80.0f
        shit.x = -80.0f
        shit.y = -80.0f

        scoreLabel.text = "Score: 0"
    }


    override fun onBackPressed() {
        //super.onBackPressed()

        val builder = AlertDialog.Builder(this)

        builder.setMessage("Are you sure to back Homepage?")

        builder.setCancelable(true)
        builder.setNegativeButton("No", DialogInterface.OnClickListener{ dialogInterface, i ->


            dialogInterface.cancel()
        })
        builder.setPositiveButton("Back Home", DialogInterface.OnClickListener{ dialogInterface, i ->
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        val alertDialog = builder.create()
        alertDialog.show()

    }



    fun changePos(){

        hitCheck()

        lemonX -= 12
        if(lemonX < 0){
            lemonX = screenWidth + 20
            lemonY = Random.nextInt(0, frameheight - lemon.height)
        }
        lemon.x = lemonX.toFloat()
        lemon.y = lemonY.toFloat()

        grapeX -= 16
        if(grapeX < 0){
            grapeX = screenWidth + 20
            grapeY = Random.nextInt(0, frameheight - grape.height)
        }
        grape.x = grapeX.toFloat()
        grape.y = grapeY.toFloat()

        shitX -= 20
        if(shitX < 0){
            shitX = screenWidth + 20
            shitY = Random.nextInt(0, frameheight - shit.height)
        }
        shit.x = shitX.toFloat()
        shit.y = shitY.toFloat()

        if(action_flg){
            boxY -= 20
        }else{
            boxY +=20
        }

        if(boxY < 0) boxY = 0
        if(boxY > frameheight - boxsize) boxY = frameheight - boxsize

        mouth.y = boxY.toFloat()

        val tmp = "Score: $score"
        scoreLabel.text = tmp
    }

    private fun hitCheck(){

        var lemonCenterX = lemonX + lemon.width/2
        var lemonCenterY = lemonY + lemon.height/2

        if(lemonCenterX in 0..boxsize && lemonCenterY in boxY..(boxY + boxsize)) {
            score += 10
            lemonX = -10
        }

        var grapeCenterX = grapeX + grape.width / 2
        var grapeCenterY = grapeY + grape.height / 2

        if (grapeCenterX in 0..boxsize && grapeCenterY in boxY..(boxY + boxsize)) {
            score += 30
            grapeX = -10
        }

        var shitCenterX = shitX + shit.width / 2
        var shitCenterY = shitY + shit.height / 2

        if (shitCenterX in 0..boxsize && shitCenterY in boxY..(boxY + boxsize)) {
            timer.cancel()
            val intent = Intent(this, EatingResultActivity::class.java)
            intent.putExtra("Score", score)
            startActivity(intent)
        }
    }

    override fun onTouchEvent(me: MotionEvent): Boolean {

        if (!start_flg) {
            start_flg = true

            var frame = findViewById<FrameLayout>(R.id.frame)
            frameheight = frame.height

            boxY = mouth.y.toInt()

            boxsize = mouth.height

            startLabel.visibility = View.GONE

            timer.schedule(object : TimerTask() {
                override fun run() {
                    handler.post { changePos() }
                }
            }, 0, 20)

        } else {
            if(me.action == MotionEvent.ACTION_DOWN) action_flg = true
            else if(me.action == MotionEvent.ACTION_UP) action_flg = false
        }

        return true
    }
}