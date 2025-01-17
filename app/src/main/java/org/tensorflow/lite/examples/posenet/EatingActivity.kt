package org.tensorflow.lite.examples.posenet

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Point
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.posenet.PosenetActivity.Companion.armspos
import java.util.*
import kotlin.random.Random

class EatingActivity: AppCompatActivity() {
    private lateinit var memmusic: MediaPlayer
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

    private var handler = Handler()
    private var timer = Timer()

    //private var action_flg = false
    private var start_flg = false

    private var baseShit = 0

    companion object{
        var score2 = 0
        var maxScoreC = 0
        var lemonC = 0
        var grapeC = 0
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eating)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.container, PosenetActivity())
            .commit()

        memmusic= MediaPlayer.create(this, R.raw.membg)
        memmusic.isLooping = true
        memmusic.setVolume(0.7f, 0.7f)
        memmusic.start()

        scoreLabel = findViewById(R.id.scoreLabel)
        startLabel = findViewById(R.id.startLabel)

        mouth = findViewById(R.id.mouth)
        lemon = findViewById(R.id.lemon)
        grape = findViewById(R.id.grape)
        shit = findViewById(R.id.shit)
        lemon.visibility = View.INVISIBLE
        grape.visibility = View.INVISIBLE
        shit.visibility = View.INVISIBLE

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



        val builder2 = AlertDialog.Builder(this)
        builder2.setTitle("Game Instruction")
        builder2.setMessage("Raise up or Put down your hand to move the mouth \n**Eat the Poopoo will end the game**")
        builder2.setPositiveButton("OK",null)
        val alertDialog = builder2.create()
        alertDialog.show()

    }

    override fun onPause() {
        super.onPause()
        memmusic.pause()
    }

    override fun onResume() {
        super.onResume()
        memmusic.start()
    }
    override fun onBackPressed() {
        //super.onBackPressed()
//        timer.cancel()
        val builder = AlertDialog.Builder(this)

        builder.setMessage("Are you sure to back Homepage?")

        builder.setCancelable(true)
        builder.setNegativeButton("No", DialogInterface.OnClickListener{ dialogInterface, i ->
            dialogInterface.cancel()
            timer.schedule(object: TimerTask() {
                override fun run() {
                    handler.post { changePos() }
                }
            },2000)
        })
        builder.setPositiveButton("Back Home", DialogInterface.OnClickListener{ dialogInterface, i ->
            timer.cancel()
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })

        val alertDialog = builder.create()
        alertDialog.show()

    }



    fun changePos(){
        hitCheck()
        lemon.visibility = View.VISIBLE
        grape.visibility = View.VISIBLE
        shit.visibility = View.VISIBLE
        lemonX -= 6
        if(lemonX < 0){
            lemonX = screenWidth + 20
            lemonY = Random.nextInt(0, frameheight - lemon.height)
        }
        lemon.x = lemonX.toFloat()
        lemon.y = lemonY.toFloat()

        grapeX -= 8
        if(grapeX < 0){
            grapeX = screenWidth + 20
            grapeY = Random.nextInt(0, frameheight - grape.height)
        }
        grape.x = grapeX.toFloat()
        grape.y = grapeY.toFloat()

        shitX -= 10
        if(shitX < 0){
            shitX = screenWidth + 20
            baseShit = (baseShit + 1) % 4
            shitY = if(baseShit == 0) frameheight - shit.height - 1//ensure endgame
            else Random.nextInt(0, frameheight - shit.height)
        }
        shit.x = shitX.toFloat()
        shit.y = shitY.toFloat()

        if(armspos[0] || armspos[1]){
            boxY -= 5
        }else{
            boxY +=5
        }

        if(boxY < 0) boxY = 0
        if(boxY > frameheight - boxsize) boxY = frameheight - boxsize

        mouth.y = boxY.toFloat()

        val tmp = "Score: $score2"
        scoreLabel.text = tmp

    }

    private fun hitCheck(){

//        var lemonCenterX = lemonX + lemon.width/2
//        var lemonCenterY = lemonY + lemon.height/2
        val lemonBottom = lemonY + lemon.height //easier to eat

        if(lemonX in 0..boxsize && (lemonY in boxY..(boxY + boxsize) || lemonBottom in boxY..(boxY + boxsize))) {
            score2 += 10
            lemonX = -10
            lemonC++
        }

//        var grapeCenterX = grapeX + grape.width / 2
//        var grapeCenterY = grapeY + grape.height / 2
        val grapeBottom = grapeY + grape.height //easier to eat

        if (grapeX in 0..boxsize && (grapeY in boxY..(boxY + boxsize) || grapeBottom in boxY..(boxY + boxsize))) {
            score2 += 30
            grapeX = -10
            grapeC++
        }

        var shitCenterX = shitX + shit.width / 2
        var shitCenterY = shitY + shit.height / 2

        if (shitCenterX in 0..boxsize && shitCenterY in boxY..(boxY + boxsize)) {
            timer.cancel()
            val intent = Intent(this, EatingResultActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onTouchEvent(me:MotionEvent): Boolean {

        if (!start_flg && me.action == MotionEvent.ACTION_DOWN) {
            start_flg = true

            var frame = findViewById<RelativeLayout>(R.id.frame)
            frameheight = frame.height

            boxY = mouth.y.toInt()

            boxsize = mouth.height

            startLabel.visibility = View.GONE

            timer.schedule(object : TimerTask() {
                override fun run() {
                    handler.post { changePos() }
                }
            }, 0, 20)

        }

        return true
    }


}
//if(me.action == MotionEvent.ACTION_DOWN) action_flg = true
//else if(me.action == MotionEvent.ACTION_UP) action_flg = false