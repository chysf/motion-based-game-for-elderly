package org.tensorflow.lite.examples.posenet

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint as AnnotationSuppressLint

import android.os.Bundle
import android.os.Handler
import android.os.Build
import android.widget.*
import android.content.Intent
import androidx.annotation.RequiresApi
import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.media.MediaPlayer
import android.util.TypedValue
import android.view.View.*
import androidx.core.content.ContextCompat
import org.tensorflow.lite.examples.posenet.PosenetActivity.Companion.handup


class MemoryActivity : AppCompatActivity() {

    private lateinit var musicTT: MediaPlayer

    private lateinit var tvp1: TextView
    private lateinit var tvp2: TextView
    private lateinit var tvp3: TextView
    private lateinit var btn1: Button
    private lateinit var btn2: Button
    private lateinit var btn3: Button
    private lateinit var container: FrameLayout
    private lateinit var layout101: RelativeLayout
    private lateinit var layout102: RelativeLayout
    private lateinit var layout103: RelativeLayout
    private lateinit var layout202: RelativeLayout

    private lateinit var img101: ImageView

    private lateinit var iv11: ImageView
    private lateinit var iv12: ImageView
    private lateinit var iv13: ImageView
    private lateinit var iv21: ImageView
    private lateinit var iv22: ImageView
    private lateinit var iv23: ImageView
    private lateinit var iv31: ImageView
    private lateinit var iv32: ImageView
    private lateinit var iv33: ImageView
    private lateinit var iv41: ImageView
    private lateinit var iv42: ImageView
    private lateinit var iv43: ImageView

    private lateinit var iv011: ImageView
    private lateinit var iv012: ImageView
    private lateinit var iv013: ImageView
    private lateinit var iv021: ImageView
    private lateinit var iv022: ImageView
    private lateinit var iv023: ImageView
    private lateinit var iv031: ImageView
    private lateinit var iv032: ImageView
    private lateinit var iv033: ImageView
    private lateinit var iv041: ImageView
    private lateinit var iv042: ImageView
    private lateinit var iv043: ImageView

    //Array for images
    private var cardsArray = listOf(101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206)

    //Time of clicking
    private var clicked00: Int = 0 //For click function instead of handup
    private var clicked01: Int = 0 //For CAM preview
    private var clicked02: Int = 0 //For the game
    private var clicked03: Int = 0 //For stopping handlers

    //STOP variables for each image button
    private var x01: Int = 0
    private var x02: Int = 0
    private var x03: Int = 0
    private var x04: Int = 0
    private var x05: Int = 0
    private var x06: Int = 0
    private var x07: Int = 0
    private var x08: Int = 0
    private var x09: Int = 0
    private var x10: Int = 0
    private var x11: Int = 0
    private var x12: Int = 0

    //Actual images
    private var image101: Int = 0
    private var image102: Int = 0
    private var image103: Int = 0
    private var image104: Int = 0
    private var image105: Int = 0
    private var image106: Int = 0
    private var image201: Int = 0
    private var image202: Int = 0
    private var image203: Int = 0
    private var image204: Int = 0
    private var image205: Int = 0
    private var image206: Int = 0

    private var firstCard: Int = 0
    private var secondCard: Int = 0
    private var clickedFirst: Int = 0
    private var clickedSecond: Int = 0
    private var cardNumber = 1

    private var mismatchPoints = 0
    private var getPoints = 0

    private var ha00 = Handler()
    private var ha11 = Handler()
    private var ha03 = Handler()
    private var ha04 = Handler()
    private var ha05 = Handler()


    override fun onStart() {
        super.onStart()
        musicTT.start()
    }

    override fun onPause() {
        super.onPause()
        musicTT.pause()
        clicked03 = 5
        layout202.visibility = INVISIBLE
        ha00.removeCallbacksAndMessages(null)
        ha11.removeCallbacksAndMessages(null)
    }


    @android.annotation.SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.container, PosenetActivity())
            .commit()

        tvp1 = findViewById(R.id.tvp1)
        tvp2 = findViewById(R.id.tvp2)
        tvp3 = findViewById(R.id.tvp3)
        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        container = findViewById(R.id.container)
        layout101 = findViewById(R.id.layout101)
        layout102 = findViewById(R.id.layout102)
        layout103 = findViewById(R.id.layout103)
        layout202 = findViewById(R.id.layout202)

        img101 = findViewById(R.id.img101)

        //Question image
        iv11 = findViewById(R.id.iv11) //ImageView #11
        iv12 = findViewById(R.id.iv12) //ImageView #12
        iv13 = findViewById(R.id.iv13) //ImageView #13
        iv21 = findViewById(R.id.iv21) //ImageView #21
        iv22 = findViewById(R.id.iv22) //ImageView #22
        iv23 = findViewById(R.id.iv23) //ImageView #23
        iv31 = findViewById(R.id.iv31) //ImageView #31
        iv32 = findViewById(R.id.iv32) //ImageView #32
        iv33 = findViewById(R.id.iv33) //ImageView #33
        iv41 = findViewById(R.id.iv41) //ImageView #41
        iv42 = findViewById(R.id.iv42) //ImageView #42
        iv43 = findViewById(R.id.iv43) //ImageView #43

        //Scissor image
        iv011 = findViewById(R.id.iv011) //ImageView #011
        iv012 = findViewById(R.id.iv012) //ImageView #012
        iv013 = findViewById(R.id.iv013) //ImageView #013
        iv021 = findViewById(R.id.iv021) //ImageView #021
        iv022 = findViewById(R.id.iv022) //ImageView #022
        iv023 = findViewById(R.id.iv023) //ImageView #023
        iv031 = findViewById(R.id.iv031) //ImageView #031
        iv032 = findViewById(R.id.iv032) //ImageView #032
        iv033 = findViewById(R.id.iv033) //ImageView #033
        iv041 = findViewById(R.id.iv041) //ImageView #041
        iv042 = findViewById(R.id.iv042) //ImageView #042
        iv043 = findViewById(R.id.iv043) //ImageView #043

        iv11.tag = "0"
        iv12.tag = "1"
        iv13.tag = "2"
        iv21.tag = "3"
        iv22.tag = "4"
        iv23.tag = "5"
        iv31.tag = "6"
        iv32.tag = "7"
        iv33.tag = "8"
        iv41.tag = "9"
        iv42.tag = "10"
        iv43.tag = "11"

        iv011.visibility = INVISIBLE
        iv012.visibility = INVISIBLE
        iv013.visibility = INVISIBLE
        iv021.visibility = INVISIBLE
        iv022.visibility = INVISIBLE
        iv023.visibility = INVISIBLE
        iv031.visibility = INVISIBLE
        iv032.visibility = INVISIBLE
        iv033.visibility = INVISIBLE
        iv041.visibility = INVISIBLE
        iv042.visibility = INVISIBLE
        iv043.visibility = INVISIBLE

        img101.visibility = INVISIBLE

        //Loading the card images
        frontOfCardsResources()

        //Shuffle the images
        val mutableList = cardsArray.toMutableList()
        mutableList.shuffle()
        cardsArray = mutableList.toList()

        //Background music
        musicTT = MediaPlayer.create(this, R.raw.mg_bgmusic)
        musicTT.isLooping = true
        musicTT.setVolume(0.7f, 0.7f)
        musicTT.start()

        //Underlay the CAM preview
        container.visibility = VISIBLE
        container.elevation = 4F
        layout101.elevation = 5F

        iv21.alpha = 0.7F
        iv22.alpha = 0.7F
        iv23.alpha = 0.7F
        iv31.alpha = 0.7F
        iv32.alpha = 0.7F
        iv33.alpha = 0.7F

        iv021.alpha = 0.7F
        iv022.alpha = 0.7F
        iv023.alpha = 0.7F
        iv031.alpha = 0.7F
        iv032.alpha = 0.7F
        iv033.alpha = 0.7F

        //Open or hide the CAM preview
        btn1.setOnClickListener {
            clicked01++
            if (clicked01%2 != 0) {
                clicked00 = 1
                container.visibility = INVISIBLE
                Toast.makeText(applicationContext, "CLICK Mode", Toast.LENGTH_SHORT).show()

                iv21.alpha = 1F
                iv22.alpha = 1F
                iv23.alpha = 1F
                iv31.alpha = 1F
                iv32.alpha = 1F
                iv33.alpha = 1F

                iv021.alpha = 1F
                iv022.alpha = 1F
                iv023.alpha = 1F
                iv031.alpha = 1F
                iv032.alpha = 1F
                iv033.alpha = 1F
            }
            if (clicked01%2 == 0){
                clicked00 = 0
                container.visibility = VISIBLE
                Toast.makeText(applicationContext, "HANDUP Mode", Toast.LENGTH_SHORT).show()

                iv21.alpha = 0.7F
                iv22.alpha = 0.7F
                iv23.alpha = 0.7F
                iv31.alpha = 0.7F
                iv32.alpha = 0.7F
                iv33.alpha = 0.7F

                iv021.alpha = 0.7F
                iv022.alpha = 0.7F
                iv023.alpha = 0.7F
                iv031.alpha = 0.7F
                iv032.alpha = 0.7F
                iv033.alpha = 0.7F
            }
        }

        btn2.setOnClickListener {
            val intent = Intent(applicationContext, MemoryActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn3.setOnClickListener {
            finish()
        }


        //Make back images flashing
        val ha = Handler()
        ha.postDelayed(object : Runnable {

            override fun run() {
                Handler().postDelayed({

                    val matrix = ColorMatrix()
                    matrix.setSaturation(2f)

                    val filter = ColorMatrixColorFilter(matrix)
                    iv11.colorFilter = filter
                    iv12.colorFilter = filter
                    iv13.colorFilter = filter
                    iv21.colorFilter = filter
                    iv22.colorFilter = filter
                    iv23.colorFilter = filter
                    iv31.colorFilter = filter
                    iv32.colorFilter = filter
                    iv33.colorFilter = filter
                    iv41.colorFilter = filter
                    iv42.colorFilter = filter
                    iv43.colorFilter = filter
                }, 1500)

                Handler().postDelayed({

                    iv11.colorFilter = null
                    iv12.colorFilter = null
                    iv13.colorFilter = null
                    iv21.colorFilter = null
                    iv22.colorFilter = null
                    iv23.colorFilter = null
                    iv31.colorFilter = null
                    iv32.colorFilter = null
                    iv33.colorFilter = null
                    iv41.colorFilter = null
                    iv42.colorFilter = null
                    iv43.colorFilter = null
                }, 3000)
                ha.postDelayed(this, 3000)
            }
        }, 3000)

        //First cycle before looping
        Handler().postDelayed({

            val matrix = ColorMatrix()
            matrix.setSaturation(2f)

            val filter = ColorMatrixColorFilter(matrix)
            iv11.colorFilter = filter
            iv12.colorFilter = filter
            iv13.colorFilter = filter
            iv21.colorFilter = filter
            iv22.colorFilter = filter
            iv23.colorFilter = filter
            iv31.colorFilter = filter
            iv32.colorFilter = filter
            iv33.colorFilter = filter
            iv41.colorFilter = filter
            iv42.colorFilter = filter
            iv43.colorFilter = filter
        }, 1500)

        Handler().postDelayed({

            iv11.colorFilter = null
            iv12.colorFilter = null
            iv13.colorFilter = null
            iv21.colorFilter = null
            iv22.colorFilter = null
            iv23.colorFilter = null
            iv31.colorFilter = null
            iv32.colorFilter = null
            iv33.colorFilter = null
            iv41.colorFilter = null
            iv42.colorFilter = null
            iv43.colorFilter = null
        }, 3000)

        //Count down 3 2 1
        Handler().postDelayed({
            layout102.elevation = 7F
            tvp2.text = "3"
        }, 1000)

        Handler().postDelayed({
            tvp2.text = "2"
        }, 2000)

        Handler().postDelayed({
            tvp2.text = "1"
        }, 3000)

        Handler().postDelayed({
            tvp2.text = "GO"
        }, 4000)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Handler().postDelayed({
                tvp2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
                tvp2.text = "Raise up your hand to harvest"
                tvp2.setBackgroundColor(0xCB1E88E5.toInt())
            }, 3000, 5000)
        }

        Handler().postDelayed({
            tvp2.text = null
        }, 8000)

        //No response after start in 1 mins 30 seconds to finish
        Handler().postDelayed({
            if (clicked02 == 0 && clicked03 == 0) {

                layout202.visibility = VISIBLE
                img101.visibility= VISIBLE
                img101.setImageResource(R.drawable.cry101)
                tvp3.text = "Time is running out!\nScore: 0"
                musicTT.pause()

                x01 = 5; x02 = 5; x03 = 5; x04 = 5; x05 = 5; x06 = 5
                x07 = 5; x08 = 5; x09 = 5; x10 = 5; x11 = 5; x12 = 5
                clicked03 = 5 //Stop this handler
            }
        }, 90000)


        //Set 12 time intervals and loop
        val ha02 = Handler()
        ha02.postDelayed(object : Runnable {

            @RequiresApi(Build.VERSION_CODES.M)
            override fun run() {
                Handler().postDelayed({
                    //Set foreground image and move to next
                    iv43.foreground = null
                    iv11.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv043.visibility = INVISIBLE
                    if (iv11.visibility == INVISIBLE) {
                        iv011.visibility = VISIBLE
                    }
                    //Use Posenet to handle 12 image buttons
                    if (clicked00 == 0) {
                        if (handup && x01 != 5 && iv43.visibility == VISIBLE) {
                            clicked02++
                            iv43.callOnClick() //Click previous image to fix Posenet delays
                            handup = false
                            x01 = 5 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    //Click function instead of handup
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x02 != 5 && iv11.visibility == VISIBLE) {
                                clicked02++
                                iv11.callOnClick() //Click previous image to fix Posenet delays
                                handup = false
                                x01 = 0 ; x02 = 5 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                },5000)
                Handler().postDelayed({
                    iv11.foreground = null
                    iv12.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv011.visibility = INVISIBLE
                    if (iv12.visibility == INVISIBLE) {
                        iv012.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x02 != 5 && iv11.visibility == VISIBLE) {
                            clicked02++
                            iv11.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 5 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x03 != 5 && iv12.visibility == VISIBLE) {
                                clicked02++
                                iv12.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 5 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 10000)
                Handler().postDelayed({
                    iv12.foreground = null
                    iv13.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv012.visibility = INVISIBLE
                    if (iv13.visibility == INVISIBLE) {
                        iv013.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x03 != 5 && iv12.visibility == VISIBLE) {
                            clicked02++
                            iv12.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 5 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x04 != 5 && iv13.visibility == VISIBLE) {
                                clicked02++
                                iv13.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 5 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 15000)
                Handler().postDelayed({
                    iv13.foreground = null
                    iv21.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv013.visibility = INVISIBLE
                    if (iv21.visibility == INVISIBLE) {
                        iv021.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x04 != 5 && iv13.visibility == VISIBLE) {
                            clicked02++
                            iv13.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 5 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x05 != 5 && iv21.visibility == VISIBLE) {
                                clicked02++
                                iv21.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 5 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 20000)
                Handler().postDelayed({
                    iv21.foreground = null
                    iv22.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv021.visibility = INVISIBLE
                    if (iv22.visibility == INVISIBLE) {
                        iv022.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x05 != 5 && iv21.visibility == VISIBLE) {
                            clicked02++
                            iv21.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 5 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x06 != 5 && iv22.visibility == VISIBLE) {
                                clicked02++
                                iv22.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 6
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 25000)
                Handler().postDelayed({
                    iv22.foreground = null
                    iv23.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv022.visibility = INVISIBLE
                    if (iv23.visibility == INVISIBLE) {
                        iv023.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x06 != 5 && iv22.visibility == VISIBLE) {
                            clicked02++
                            iv22.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 5
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x07 != 5 && iv23.visibility == VISIBLE) {
                                clicked02++
                                iv23.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 5 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 30000)
                Handler().postDelayed({
                    iv23.foreground = null
                    iv31.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv023.visibility = INVISIBLE
                    if (iv31.visibility == INVISIBLE) {
                        iv031.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x07 != 5 && iv23.visibility == VISIBLE) {
                            clicked02++
                            iv23.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 5 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x08 != 5 && iv31.visibility == VISIBLE) {
                                clicked02++
                                iv31.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 5 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 35000)
                Handler().postDelayed({
                    iv31.foreground = null
                    iv32.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv031.visibility = INVISIBLE
                    if (iv32.visibility == INVISIBLE) {
                        iv032.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x08 != 5 && iv31.visibility == VISIBLE) {
                            clicked02++
                            iv31.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 5 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x09 != 5 && iv32.visibility == VISIBLE) {
                                clicked02++
                                iv32.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 5 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 40000)
                Handler().postDelayed({
                    iv32.foreground = null
                    iv33.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv032.visibility = INVISIBLE
                    if (iv33.visibility == INVISIBLE) {
                        iv033.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x09 != 5 && iv32.visibility == VISIBLE) {
                            clicked02++
                            iv32.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 5 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x10 != 5 && iv33.visibility == VISIBLE) {
                                clicked02++
                                iv33.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 5 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 45000)
                Handler().postDelayed({
                    iv33.foreground = null
                    iv41.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv033.visibility = INVISIBLE
                    if (iv41.visibility == INVISIBLE) {
                        iv041.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x10 != 5 && iv33.visibility == VISIBLE) {
                            clicked02++
                            iv33.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 5 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x11 != 5 && iv41.visibility == VISIBLE) {
                                clicked02++
                                iv41.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 5 ; x12 = 0
                            }
                        }
                    }
                }, 50000)
                Handler().postDelayed({
                    iv41.foreground = null
                    iv42.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv041.visibility = INVISIBLE
                    if (iv42.visibility == INVISIBLE) {
                        iv042.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x11 != 5 && iv41.visibility == VISIBLE) {
                            clicked02++
                            iv41.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 5 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x12 != 5 && iv42.visibility == VISIBLE) {
                                clicked02++
                                iv42.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 5
                            }
                        }
                    }
                }, 55000)
                Handler().postDelayed({
                    iv42.foreground = null
                    iv43.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv042.visibility = INVISIBLE
                    if (iv43.visibility == INVISIBLE) {
                        iv043.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x12 != 5 && iv42.visibility == VISIBLE) {
                            clicked02++
                            iv42.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 5
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x01 != 5 && iv43.visibility == VISIBLE) {
                                clicked02++
                                iv43.callOnClick()
                                handup = false
                                x01 =  ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 60000)
                ha02.postDelayed(this, 60000)
            }
        }, 60000)

        //First cycle before looping
        Handler().postDelayed({
        ha02.postDelayed(object : Runnable {

            @RequiresApi(Build.VERSION_CODES.M)
            override fun run() {
                Handler().postDelayed({
                    //Set foreground image and move to next
                    iv43.foreground = null
                    iv11.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv043.visibility = INVISIBLE
                    if (iv11.visibility == INVISIBLE) {
                        iv011.visibility = VISIBLE
                    }
                    //Use Posenet to handle 12 image buttons
                    if (clicked00 == 0) {
                        if (handup && x02 != 5 && iv43.visibility == VISIBLE) {
                            clicked02++
                            iv11.callOnClick() //Click previous image to fix Posenet delays
                            handup = false
                            x01 = 0 ; x02 = 5 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    //Click function instead of handup
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x02 != 5 && iv11.visibility == VISIBLE) {
                                clicked02++
                                iv11.callOnClick() //Click previous image to fix Posenet delays
                                handup = false
                                x01 = 0 ; x02 = 5 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                },5000)
                Handler().postDelayed({
                    iv11.foreground = null
                    iv12.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv011.visibility = INVISIBLE
                    if (iv12.visibility == INVISIBLE) {
                        iv012.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x02 != 5 && iv11.visibility == VISIBLE) {
                            clicked02++
                            iv11.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 5 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x03 != 5 && iv12.visibility == VISIBLE) {
                                clicked02++
                                iv12.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 5 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 10000)
                Handler().postDelayed({
                    iv12.foreground = null
                    iv13.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv012.visibility = INVISIBLE
                    if (iv13.visibility == INVISIBLE) {
                        iv013.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x03 != 5 && iv12.visibility == VISIBLE) {
                            clicked02++
                            iv12.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 5 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x04 != 5 && iv13.visibility == VISIBLE) {
                                clicked02++
                                iv13.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 5 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 15000)
                Handler().postDelayed({
                    iv13.foreground = null
                    iv21.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv013.visibility = INVISIBLE
                    if (iv21.visibility == INVISIBLE) {
                        iv021.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x04 != 5 && iv13.visibility == VISIBLE) {
                            clicked02++
                            iv13.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 5 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x05 != 5 && iv21.visibility == VISIBLE) {
                                clicked02++
                                iv21.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 5 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 20000)
                Handler().postDelayed({
                    iv21.foreground = null
                    iv22.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv021.visibility = INVISIBLE
                    if (iv22.visibility == INVISIBLE) {
                        iv022.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x05 != 5 && iv21.visibility == VISIBLE) {
                            clicked02++
                            iv21.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 5 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x06 != 5 && iv22.visibility == VISIBLE) {
                                clicked02++
                                iv22.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 6
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 25000)
                Handler().postDelayed({
                    iv22.foreground = null
                    iv23.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv022.visibility = INVISIBLE
                    if (iv23.visibility == INVISIBLE) {
                        iv023.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x06 != 5 && iv22.visibility == VISIBLE) {
                            clicked02++
                            iv22.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 5
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x07 != 5 && iv23.visibility == VISIBLE) {
                                clicked02++
                                iv23.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 5 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 30000)
                Handler().postDelayed({
                    iv23.foreground = null
                    iv31.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv023.visibility = INVISIBLE
                    if (iv31.visibility == INVISIBLE) {
                        iv031.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x07 != 5 && iv23.visibility == VISIBLE) {
                            clicked02++
                            iv23.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 5 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x08 != 5 && iv31.visibility == VISIBLE) {
                                clicked02++
                                iv31.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 5 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 35000)
                Handler().postDelayed({
                    iv31.foreground = null
                    iv32.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv031.visibility = INVISIBLE
                    if (iv32.visibility == INVISIBLE) {
                        iv032.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x08 != 5 && iv31.visibility == VISIBLE) {
                            clicked02++
                            iv31.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 5 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x09 != 5 && iv32.visibility == VISIBLE) {
                                clicked02++
                                iv32.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 5 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 40000)
                Handler().postDelayed({
                    iv32.foreground = null
                    iv33.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv032.visibility = INVISIBLE
                    if (iv33.visibility == INVISIBLE) {
                        iv033.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x09 != 5 && iv32.visibility == VISIBLE) {
                            clicked02++
                            iv32.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 5 ; x10 = 0 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x10 != 5 && iv33.visibility == VISIBLE) {
                                clicked02++
                                iv33.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 5 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 45000)
                Handler().postDelayed({
                    iv33.foreground = null
                    iv41.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv033.visibility = INVISIBLE
                    if (iv41.visibility == INVISIBLE) {
                        iv041.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x10 != 5 && iv33.visibility == VISIBLE) {
                            clicked02++
                            iv33.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 5 ; x11 = 0 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x11 != 5 && iv41.visibility == VISIBLE) {
                                clicked02++
                                iv41.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 5 ; x12 = 0
                            }
                        }
                    }
                }, 50000)
                Handler().postDelayed({
                    iv41.foreground = null
                    iv42.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv041.visibility = INVISIBLE
                    if (iv42.visibility == INVISIBLE) {
                        iv042.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x11 != 5 && iv41.visibility == VISIBLE) {
                            clicked02++
                            iv41.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 5 ; x12 = 0
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x12 != 5 && iv42.visibility == VISIBLE) {
                                clicked02++
                                iv42.callOnClick()
                                handup = false
                                x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 5
                            }
                        }
                    }
                }, 55000)
                Handler().postDelayed({
                    iv42.foreground = null
                    iv43.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv042.visibility = INVISIBLE
                    if (iv43.visibility == INVISIBLE) {
                        iv043.visibility = VISIBLE
                    }
                    if (clicked00 == 0) {
                        if (handup && x12 != 5 && iv42.visibility == VISIBLE) {
                            clicked02++
                            iv42.callOnClick()
                            handup = false
                            x01 = 0 ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                            x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 5
                        }
                    }
                    if (clicked00 == 1) {
                        layout103.setOnClickListener {
                            if (x01 != 5 && iv43.visibility == VISIBLE) {
                                clicked02++
                                iv43.callOnClick()
                                handup = false
                                x01 =  ; x02 = 0 ; x03 = 0 ; x04 = 0 ; x05 = 0 ; x06 = 0
                                x07 = 0 ; x08 = 0 ; x09 = 0 ; x10 = 0 ; x11 = 0 ; x12 = 0
                            }
                        }
                    }
                }, 60000)
            


        iv11.isEnabled = false
        iv12.isEnabled = false
        iv13.isEnabled = false
        iv21.isEnabled = false
        iv22.isEnabled = false
        iv23.isEnabled = false
        iv31.isEnabled = false
        iv32.isEnabled = false
        iv33.isEnabled = false
        iv41.isEnabled = false
        iv42.isEnabled = false
        iv43.isEnabled = false


        //Set ImageView OnClick
        iv11.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv11, theCard)
        }

        iv12.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv12, theCard)
        }

        iv13.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv13, theCard)
        }

        iv21.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv21, theCard)
        }

        iv22.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv22, theCard)
        }

        iv23.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv23, theCard)
        }

        iv31.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv31, theCard)
        }

        iv32.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv32, theCard)
        }

        iv33.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv33, theCard)
        }

        iv41.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv41, theCard)
        }

        iv42.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv42, theCard)
        }
        iv43.setOnClickListener { view ->
            val theCard = Integer.parseInt(view.tag as String)
            doStuff(iv43, theCard)
        }
    }


    @android.annotation.SuppressLint("SetTextI18n")
    private fun doStuff(iv: ImageView, card: Int) {

        //Set the correct image to the ImageView
        when {
            cardsArray[card] == 101 -> iv.setImageResource(image101)
            cardsArray[card] == 102 -> iv.setImageResource(image102)
            cardsArray[card] == 103 -> iv.setImageResource(image103)
            cardsArray[card] == 104 -> iv.setImageResource(image104)
            cardsArray[card] == 105 -> iv.setImageResource(image105)
            cardsArray[card] == 106 -> iv.setImageResource(image106)
            cardsArray[card] == 201 -> iv.setImageResource(image201)
            cardsArray[card] == 202 -> iv.setImageResource(image202)
            cardsArray[card] == 203 -> iv.setImageResource(image203)
            cardsArray[card] == 204 -> iv.setImageResource(image204)
            cardsArray[card] == 205 -> iv.setImageResource(image205)
            cardsArray[card] == 206 -> iv.setImageResource(image206)
        }

        if (cardNumber == 1 || cardNumber == 2) {

            //Shake front images when click
            Handler().postDelayed({
                iv.animate().rotation(30F).setDuration(250).start()
            }, 0)
            Handler().postDelayed({
                iv.animate().rotation(-30F).setDuration(250).start()
            }, 250)
            Handler().postDelayed({
                iv.animate().rotation(30F).setDuration(250).start()
            }, 500)
            Handler().postDelayed({
                iv.animate().rotation(0F).setDuration(250).start()
            }, 750)
        }

        //Check which image is selected and save it temporarily
        if (cardNumber == 1) {
            firstCard = cardsArray[card]
            if (firstCard > 200) {
                firstCard -= 100
            }
            cardNumber = 2
            clickedFirst = card


            //No response after first clicked in 1 mins 30 seconds to finish
            if (clicked02%2 !=0) {

                ha00 = Handler()
                ha00.postDelayed({
                    if (clicked03 == 0) {
                        val pref = getSharedPreferences("Game_Data", Context.MODE_PRIVATE)
                        val maxScore = pref?.getInt("MaxScore3", 0)
                        if (getPoints > maxScore!!) {
                            pref.edit()?.putInt("MaxScore3", getPoints)?.apply()
                        }
                        layout202.visibility = VISIBLE
                        img101.visibility= VISIBLE
                        img101.setImageResource(R.drawable.cry101)
                        tvp3.text = "Time is running out!\nScore: $getPoints"
                        musicTT.pause()

                        x01 = 5; x02 = 5; x03 = 5; x04 = 5; x05 = 5; x06 = 5
                        x07 = 5; x08 = 5; x09 = 5; x10 = 5; x11 = 5; x12 = 5
                        clicked03 = 5 //Stop this handler
                    }
                }, 90000)
                ha11.removeCallbacksAndMessages(null)
            }

            //Keep cardNumber 1 front images brighter
            ha03 = Handler()
            ha03.postDelayed(object : Runnable {

                @RequiresApi(Build.VERSION_CODES.M)
                override fun run() {

                    iv.foreground = null

                    val matrix01 = ColorMatrix()
                    matrix01.setSaturation(2f)

                    val filter01 = ColorMatrixColorFilter(matrix01)
                    iv.colorFilter = filter01

                    ha03.postDelayed(this, 0)
                }
            }, 0)

            //Shake the front image again if no response after first click
            ha04 = Handler()
            ha04.postDelayed(object : Runnable {

                @android.annotation.SuppressLint("SetTextI18n")
                override fun run() {
                    Handler().postDelayed({
                        tvp2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30F)
                        tvp2.text = "What are you waiting for?"
                        tvp2.setBackgroundColor(0xCBFF166B.toInt())
                        iv.animate().rotation(30F).setDuration(250).start()
                    }, 0)
                    Handler().postDelayed({
                        iv.animate().rotation(-30F).setDuration(250).start()
                    }, 250)
                    Handler().postDelayed({
                        iv.animate().rotation(30F).setDuration(250).start()
                    }, 500)
                    Handler().postDelayed({
                        iv.animate().rotation(0F).setDuration(250).start()
                    }, 750)
                    Handler().postDelayed({
                        tvp2.text = null
                    }, 3000)
                    ha04.postDelayed(this, 60000)
                }
            }, 60000)


        } else if (cardNumber == 2) {
            secondCard = cardsArray[card]
            if (secondCard > 200) {
                secondCard -= 100
            }
            cardNumber = 1
            clickedSecond = card


            //Keep cardNumber 1 front images brighter
            ha05 = Handler()
            ha05.postDelayed(object : Runnable {

                @RequiresApi(Build.VERSION_CODES.M)
                override fun run() {

                    iv.foreground = null

                    val matrix02 = ColorMatrix()
                    matrix02.setSaturation(2f)

                    val filter02 = ColorMatrixColorFilter(matrix02)
                    iv.colorFilter = filter02

                    ha05.postDelayed(this, 0)
                }
            }, 0)

            if (clicked02%2 == 0) {

                //No response after second clicked in 1 mins 30 seconds to finish
                ha11 = Handler()
                ha11.postDelayed({
                    if (clicked03 == 0) {
                        val pref = getSharedPreferences("Game_Data", Context.MODE_PRIVATE)
                        val maxScore = pref?.getInt("MaxScore3", 0)
                        if (getPoints > maxScore!!) {
                            pref.edit()?.putInt("MaxScore3", getPoints)?.apply()
                        }
                        layout202.visibility = VISIBLE
                        img101.visibility= VISIBLE
                        img101.setImageResource(R.drawable.cry101)
                        tvp3.text = "Time is running out!\nScore: $getPoints"
                        musicTT.pause()

                        x01 = 5; x02 = 5; x03 = 5; x04 = 5; x05 = 5; x06 = 5
                        x07 = 5; x08 = 5; x09 = 5; x10 = 5; x11 = 5; x12 = 5
                        clicked03 = 5 //Stop this handler
                    }
                }, 90000)

                //Shut down those handlers
                ha00.removeCallbacksAndMessages(null)
                ha03.removeCallbacksAndMessages(null)
                ha04.removeCallbacksAndMessages(null)

                tvp2.text = null
            }

            val handler = Handler()
            handler.postDelayed({

                ha05.removeCallbacksAndMessages(null)
                
                //Check if the images that are selected are equal
                calculate()
            }, 2000)
        }
    }

    @AnnotationSuppressLint("SetTextI18n")
    private fun calculate() {
        //If Images are equal remove them otherwise add Mismatch point
        if (firstCard == secondCard) {
            getPoints++

           when (clickedFirst) {
                0 -> iv11.visibility = INVISIBLE
                1 -> iv12.visibility = INVISIBLE
                2 -> iv13.visibility = INVISIBLE
                3 -> iv21.visibility = INVISIBLE
                4 -> iv22.visibility = INVISIBLE
                5 -> iv23.visibility = INVISIBLE
                6 -> iv31.visibility = INVISIBLE
                7 -> iv32.visibility = INVISIBLE
                8 -> iv33.visibility = INVISIBLE
                9 -> iv41.visibility = INVISIBLE
                10 -> iv42.visibility = INVISIBLE
                11 -> iv43.visibility = INVISIBLE
            }

            when (clickedSecond) {
                0 -> iv11.visibility = INVISIBLE
                1 -> iv12.visibility = INVISIBLE
                2 -> iv13.visibility = INVISIBLE
                3 -> iv21.visibility = INVISIBLE
                4 -> iv22.visibility = INVISIBLE
                5 -> iv23.visibility = INVISIBLE
                6 -> iv31.visibility = INVISIBLE
                7 -> iv32.visibility = INVISIBLE
                8 -> iv33.visibility = INVISIBLE
                9 -> iv41.visibility = INVISIBLE
                10 -> iv42.visibility = INVISIBLE
                11 -> iv43.visibility = INVISIBLE
            }

        } else {
            mismatchPoints++
            if (mismatchPoints == 1) {
                tvp1.text = "❤❤❤❤❤  "
            }
            if (mismatchPoints == 2) {
                tvp1.text = "❤❤❤❤    "
            }
            if (mismatchPoints == 3) {
                tvp1.text = "❤❤❤      "
            }
            if (mismatchPoints == 4) {
                tvp1.text = "❤❤        "
            }
            if (mismatchPoints == 5) {
                tvp1.text = "❤          "
            }
            if (mismatchPoints == 6) {
                tvp1.text = "No more chance!"

                val pref = getSharedPreferences("Game_Data", Context.MODE_PRIVATE)
                val maxScore = pref?.getInt("MaxScore3", 0)
                if(getPoints > maxScore!!){
                    pref.edit()?.putInt("MaxScore3", getPoints)?.apply()
                }
                layout202.visibility = VISIBLE
                img101.visibility= VISIBLE
                img101.setImageResource(R.drawable.cry102)
                tvp3.text = "GAME OVER!\nScore: $getPoints"

                musicTT.pause()

                x01 = 5 ; x02 = 5 ; x03 = 5 ; x04 = 5 ; x05 = 5 ; x06 = 5
                x07 = 5 ; x08 = 5 ; x09 = 5 ; x10 = 5 ; x11 = 5 ; x12 = 5
                clicked03 = 5
            }

            iv11.setImageResource(R.drawable.ic_question)
            iv12.setImageResource(R.drawable.ic_question)
            iv13.setImageResource(R.drawable.ic_question)
            iv21.setImageResource(R.drawable.ic_question)
            iv22.setImageResource(R.drawable.ic_question)
            iv23.setImageResource(R.drawable.ic_question)
            iv31.setImageResource(R.drawable.ic_question)
            iv32.setImageResource(R.drawable.ic_question)
            iv33.setImageResource(R.drawable.ic_question)
            iv41.setImageResource(R.drawable.ic_question)
            iv42.setImageResource(R.drawable.ic_question)
            iv43.setImageResource(R.drawable.ic_question)

        }

        //Check if the Game is Over
        checkEnd()
    }

    @android.annotation.SuppressLint("SetTextI18n")
    private fun checkEnd() {
        if (iv11.visibility == INVISIBLE &&
            iv12.visibility == INVISIBLE &&
            iv13.visibility == INVISIBLE &&
            iv21.visibility == INVISIBLE &&
            iv22.visibility == INVISIBLE &&
            iv23.visibility == INVISIBLE &&
            iv31.visibility == INVISIBLE &&
            iv32.visibility == INVISIBLE &&
            iv33.visibility == INVISIBLE &&
            iv41.visibility == INVISIBLE &&
            iv42.visibility == INVISIBLE &&
            iv43.visibility == INVISIBLE
        )

        {
            val pref = getSharedPreferences("Game_Data", Context.MODE_PRIVATE)
            if(getPoints == 6){
                pref.edit()?.putInt("MaxScore3", getPoints)?.apply()
            }
            layout202.visibility = VISIBLE
            img101.visibility= VISIBLE
            img101.setImageResource(R.drawable.congrats)
            tvp3.text = "Congratulation!\nYou win!"

            musicTT.pause()

            x01 = 5 ; x02 = 5 ; x03 = 5 ; x04 = 5 ; x05 = 5 ; x06 = 5
            x07 = 5 ; x08 = 5 ; x09 = 5 ; x10 = 5 ; x11 = 5 ; x12 = 5
            clicked03 = 5
        }
    }

    private fun frontOfCardsResources() {

        image101 = R.drawable.ic_img101
        image102 = R.drawable.ic_img102
        image103 = R.drawable.ic_img103
        image104 = R.drawable.ic_img104
        image105 = R.drawable.ic_img105
        image106 = R.drawable.ic_img106
        image201 = R.drawable.ic_img201
        image202 = R.drawable.ic_img202
        image203 = R.drawable.ic_img203
        image204 = R.drawable.ic_img204
        image205 = R.drawable.ic_img205
        image206 = R.drawable.ic_img206

    }

}
