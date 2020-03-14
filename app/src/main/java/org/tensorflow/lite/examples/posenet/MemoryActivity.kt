package org.tensorflow.lite.examples.posenet

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint as AnnotationSuppressLint

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import android.content.Intent
import android.view.KeyEvent
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private lateinit var tvp1: TextView
    private lateinit var btn1: Button
    private lateinit var layoutRR: RelativeLayout

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
    private var clicked01: Int = 0
    private var clicked02: Int = 0
    private var clicked03: Int = 0
    private var clicked04: Int = 0
    private var clicked05: Int = 0
    private var clicked06: Int = 0
    private var clicked07: Int = 0
    private var clicked08: Int = 0
    private var clicked09: Int = 0
    private var clicked10: Int = 0
    private var clicked11: Int = 0
    private var clicked12: Int = 0

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

    //Close Activity when BACK clicked
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {

        return when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {

                stopService(Intent(this, MusicService::class.java))
                this.finish()

                true
            }
            else -> super.onKeyDown(keyCode, event)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory)

        //Close the app
        btn1 = findViewById(R.id.btn1)
        btn1.setOnClickListener {

            val alertDialogBuilder = AlertDialog.Builder(this@MemoryActivity)
            alertDialogBuilder
                .setMessage("Are you sure you want to leave?")
                .setCancelable(true)
                .setPositiveButton("No") { _, _ ->
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.cancel()
                }
                .setNegativeButton("Yes") { _, _ -> finish()
                    stopService(Intent(this, MusicService::class.java))}
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        tvp1 = findViewById(R.id.tvp1)
        layoutRR = findViewById(R.id.layoutRR)

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

        iv011.visibility = View.INVISIBLE
        iv012.visibility = View.INVISIBLE
        iv013.visibility = View.INVISIBLE
        iv021.visibility = View.INVISIBLE
        iv022.visibility = View.INVISIBLE
        iv023.visibility = View.INVISIBLE
        iv031.visibility = View.INVISIBLE
        iv032.visibility = View.INVISIBLE
        iv033.visibility = View.INVISIBLE
        iv041.visibility = View.INVISIBLE
        iv042.visibility = View.INVISIBLE
        iv043.visibility = View.INVISIBLE


        //Loading the card images
        frontOfCardsResources()

        //Shuffle the images
        val mutableList = cardsArray.toMutableList()
        mutableList.shuffle()
        cardsArray = mutableList.toList()

        //Background music
        val music = Intent()
        music.setClass(this, MusicService::class.java)
        startService(music)


            //Set 12 time intervals and loop
        val ha = Handler()
        ha.postDelayed(object : Runnable {

            override fun run() {
                Handler().postDelayed({
                    //Set foreground image and move to next
                    iv43.foreground = null
                    iv11.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv043.visibility = View.INVISIBLE
                    if(iv11.visibility == View.INVISIBLE) {
                        iv011.visibility = View.VISIBLE
                    }
                    //Use one button to handle 12 image buttons
                    layoutRR.setOnClickListener {
                        clicked01++
                        if (clicked01 == 1 && iv11.visibility == View.VISIBLE) {
                            iv11.callOnClick()
                        }}
                    //Check if first click and second click are not the same image
                    if (clicked02 > 0 || clicked03 > 0 || clicked04 > 0 ||
                        clicked05 > 0 || clicked06 > 0 || clicked07 > 0 ||
                        clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                        clicked11 > 0 || clicked12 > 0 ) {
                        clicked01 = 0
                    }
                },1000)
                Handler().postDelayed({
                    iv11.foreground = null
                    iv12.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv011.visibility = View.INVISIBLE
                    if(iv12.visibility == View.INVISIBLE) {
                        iv012.visibility = View.VISIBLE
                    }
                    layoutRR.setOnClickListener {
                        clicked02++
                        if (clicked02 == 1 && iv12.visibility == View.VISIBLE) {
                            iv12.callOnClick()
                        }}
                    if (clicked02 > 0 || clicked03 > 0 || clicked04 > 0 ||
                        clicked05 > 0 || clicked06 > 0 || clicked07 > 0 ||
                        clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                        clicked11 > 0 || clicked12 > 0 ) {
                        clicked02 = 0
                    }
                }, 2000)
                Handler().postDelayed({
                    iv12.foreground = null
                    iv13.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv012.visibility = View.INVISIBLE
                    if(iv13.visibility == View.INVISIBLE) {
                        iv013.visibility = View.VISIBLE
                    }
                    layoutRR.setOnClickListener {
                        clicked03++
                        if (clicked03 == 1 && iv13.visibility == View.VISIBLE) {
                            iv13.callOnClick()
                        }}
                    if (clicked01 > 0 || clicked02 > 0 || clicked04 > 0 ||
                        clicked05 > 0 || clicked06 > 0 || clicked07 > 0 ||
                        clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                        clicked11 > 0 || clicked12 > 0 ) {
                        clicked03 = 0
                    }
                }, 3000)
                Handler().postDelayed({
                    iv13.foreground = null
                    iv21.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv013.visibility = View.INVISIBLE
                    if(iv21.visibility == View.INVISIBLE) {
                        iv021.visibility = View.VISIBLE
                    }
                    layoutRR.setOnClickListener {
                        clicked04++
                        if (clicked04 == 1 && iv21.visibility == View.VISIBLE) {
                            iv21.callOnClick()
                        }}
                    if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                        clicked05 > 0 || clicked06 > 0 || clicked07 > 0 ||
                        clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                        clicked11 > 0 || clicked12 > 0 ) {
                        clicked04 = 0
                    }
                }, 4000)
                Handler().postDelayed({
                    iv21.foreground = null
                    iv22.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv021.visibility = View.INVISIBLE
                    if(iv22.visibility == View.INVISIBLE) {
                        iv022.visibility = View.VISIBLE
                    }
                    layoutRR.setOnClickListener {
                        clicked05++
                        if (clicked05 == 1 && iv22.visibility == View.VISIBLE) {
                            iv22.callOnClick()
                        }}
                    if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                        clicked04 > 0 || clicked06 > 0 || clicked07 > 0 ||
                        clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                        clicked11 > 0 || clicked12 > 0 ) {
                        clicked05 = 0
                    }
                }, 5000)
                Handler().postDelayed({
                    iv22.foreground = null
                    iv23.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv022.visibility = View.INVISIBLE
                    if(iv23.visibility == View.INVISIBLE) {
                        iv023.visibility = View.VISIBLE
                    }
                    layoutRR.setOnClickListener {
                        clicked06++
                        if (clicked06 == 1 && iv23.visibility == View.VISIBLE) {
                            iv23.callOnClick()
                        }}
                    if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                        clicked04 > 0 || clicked05 > 0 || clicked07 > 0 ||
                        clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                        clicked11 > 0 || clicked12 > 0 ) {
                        clicked06 = 0
                    }
                }, 6000)
                Handler().postDelayed({
                    iv23.foreground = null
                    iv31.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv023.visibility = View.INVISIBLE
                    if(iv31.visibility == View.INVISIBLE) {
                        iv031.visibility = View.VISIBLE
                    }
                    layoutRR.setOnClickListener {
                        clicked07++
                        if (clicked07 == 1 && iv31.visibility == View.VISIBLE) {
                            iv31.callOnClick()
                        }}
                    if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                        clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                        clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                        clicked11 > 0 || clicked12 > 0 ) {
                        clicked07 = 0
                    }
                }, 7000)
                Handler().postDelayed({
                    iv31.foreground = null
                    iv32.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv031.visibility = View.INVISIBLE
                    if(iv32.visibility == View.INVISIBLE) {
                        iv032.visibility = View.VISIBLE
                    }
                    layoutRR.setOnClickListener {
                        clicked08++
                        if (clicked08 == 1 && iv32.visibility == View.VISIBLE) {
                            iv32.callOnClick()
                        }}
                    if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                        clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                        clicked07 > 0 || clicked09 > 0 || clicked10 > 0 ||
                        clicked11 > 0 || clicked12 > 0 ) {
                        clicked08 = 0
                    }
                }, 8000)
                Handler().postDelayed({
                    iv32.foreground = null
                    iv33.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv032.visibility = View.INVISIBLE
                    if(iv33.visibility == View.INVISIBLE) {
                        iv033.visibility = View.VISIBLE
                    }
                    layoutRR.setOnClickListener {
                        clicked09++
                        if (clicked09 == 1 && iv33.visibility == View.VISIBLE) {
                            iv33.callOnClick()
                        }}
                    if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                        clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                        clicked07 > 0 || clicked08 > 0 || clicked10 > 0 ||
                        clicked11 > 0 || clicked12 > 0 ) {
                        clicked09 = 0
                    }
                }, 9000)
                Handler().postDelayed({
                    iv33.foreground = null
                    iv41.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv033.visibility = View.INVISIBLE
                    if(iv41.visibility == View.INVISIBLE) {
                        iv041.visibility = View.VISIBLE
                    }
                    layoutRR.setOnClickListener {
                        clicked10++
                        if (clicked10 == 1 && iv41.visibility == View.VISIBLE) {
                            iv41.callOnClick()
                        }}
                    if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                        clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                        clicked07 > 0 || clicked08 > 0 || clicked09 > 0 ||
                        clicked11 > 0 || clicked12 > 0 ) {
                        clicked10 = 0
                    }
                }, 10000)
                Handler().postDelayed({
                    iv41.foreground = null
                    iv42.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv041.visibility = View.INVISIBLE
                    if(iv42.visibility == View.INVISIBLE) {
                        iv042.visibility = View.VISIBLE
                    }
                    layoutRR.setOnClickListener {
                        clicked11++
                        if (clicked11 == 1 && iv42.visibility == View.VISIBLE) {
                            iv42.callOnClick()
                        }}
                    if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                        clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                        clicked07 > 0 || clicked08 > 0 || clicked09 > 0 ||
                        clicked10 > 0 || clicked12 > 0 ) {
                        clicked11 = 0
                    }
                }, 11000)
                Handler().postDelayed({
                    iv42.foreground = null
                    iv43.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
                    iv042.visibility = View.INVISIBLE
                    if(iv43.visibility == View.INVISIBLE) {
                        iv043.visibility = View.VISIBLE
                    }
                    layoutRR.setOnClickListener {
                        clicked12++
                        if (clicked12 == 1 && iv43.visibility == View.VISIBLE) {
                            iv43.callOnClick()
                        }}
                    if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                        clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                        clicked07 > 0 || clicked08 > 0 || clicked09 > 0 ||
                        clicked10 > 0 || clicked11 > 0 ) {
                        clicked12 = 0
                    }
                }, 12000)
                ha.postDelayed(this, 12000)
            }
        }, 12000)

        //First cycle of looping
        Handler().postDelayed({
            //Set foreground image and move to next
            iv43.foreground = null
            iv11.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv043.visibility = View.INVISIBLE
            if(iv11.visibility == View.INVISIBLE) {
                iv011.visibility = View.VISIBLE
            }
            //Use one button to handle 12 image buttons
            layoutRR.setOnClickListener {
                clicked01++
                if (clicked01 == 1 && iv11.visibility == View.VISIBLE) {
                    iv11.callOnClick()
                }}
            //Check if first click and second click are not the same image
            if (clicked02 > 0 || clicked03 > 0 || clicked04 > 0 ||
                clicked05 > 0 || clicked06 > 0 || clicked07 > 0 ||
                clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                clicked11 > 0 || clicked12 > 0 ) {
                clicked01 = 0
            }
        },1000)
        Handler().postDelayed({
            iv11.foreground = null
            iv12.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv011.visibility = View.INVISIBLE
            if(iv12.visibility == View.INVISIBLE) {
                iv012.visibility = View.VISIBLE
            }
            layoutRR.setOnClickListener {
                clicked02++
                if (clicked02 == 1 && iv12.visibility == View.VISIBLE) {
                    iv12.callOnClick()
                }}
            if (clicked02 > 0 || clicked03 > 0 || clicked04 > 0 ||
                clicked05 > 0 || clicked06 > 0 || clicked07 > 0 ||
                clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                clicked11 > 0 || clicked12 > 0 ) {
                clicked02 = 0
            }
        }, 2000)
        Handler().postDelayed({
            iv12.foreground = null
            iv13.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv012.visibility = View.INVISIBLE
            if(iv13.visibility == View.INVISIBLE) {
                iv013.visibility = View.VISIBLE
            }
            layoutRR.setOnClickListener {
                clicked03++
                if (clicked03 == 1 && iv13.visibility == View.VISIBLE) {
                    iv13.callOnClick()
                }}
            if (clicked01 > 0 || clicked02 > 0 || clicked04 > 0 ||
                clicked05 > 0 || clicked06 > 0 || clicked07 > 0 ||
                clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                clicked11 > 0 || clicked12 > 0 ) {
                clicked03 = 0
            }
        }, 3000)
        Handler().postDelayed({
            iv13.foreground = null
            iv21.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv013.visibility = View.INVISIBLE
            if(iv21.visibility == View.INVISIBLE) {
                iv021.visibility = View.VISIBLE
            }
            layoutRR.setOnClickListener {
                clicked04++
                if (clicked04 == 1 && iv21.visibility == View.VISIBLE) {
                    iv21.callOnClick()
                }}
            if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                clicked05 > 0 || clicked06 > 0 || clicked07 > 0 ||
                clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                clicked11 > 0 || clicked12 > 0 ) {
                clicked04 = 0
            }
        }, 4000)
        Handler().postDelayed({
            iv21.foreground = null
            iv22.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv021.visibility = View.INVISIBLE
            if(iv22.visibility == View.INVISIBLE) {
                iv022.visibility = View.VISIBLE
            }
            layoutRR.setOnClickListener {
                clicked05++
                if (clicked05 == 1 && iv22.visibility == View.VISIBLE) {
                    iv22.callOnClick()
                }}
            if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                clicked04 > 0 || clicked06 > 0 || clicked07 > 0 ||
                clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                clicked11 > 0 || clicked12 > 0 ) {
                clicked05 = 0
            }
        }, 5000)
        Handler().postDelayed({
            iv22.foreground = null
            iv23.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv022.visibility = View.INVISIBLE
            if(iv23.visibility == View.INVISIBLE) {
                iv023.visibility = View.VISIBLE
            }
            layoutRR.setOnClickListener {
                clicked06++
                if (clicked06 == 1 && iv23.visibility == View.VISIBLE) {
                    iv23.callOnClick()
                }}
            if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                clicked04 > 0 || clicked05 > 0 || clicked07 > 0 ||
                clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                clicked11 > 0 || clicked12 > 0 ) {
                clicked06 = 0
            }
        }, 6000)
        Handler().postDelayed({
            iv23.foreground = null
            iv31.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv023.visibility = View.INVISIBLE
            if(iv31.visibility == View.INVISIBLE) {
                iv031.visibility = View.VISIBLE
            }
            layoutRR.setOnClickListener {
                clicked07++
                if (clicked07 == 1 && iv31.visibility == View.VISIBLE) {
                    iv31.callOnClick()
                }}
            if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                clicked08 > 0 || clicked09 > 0 || clicked10 > 0 ||
                clicked11 > 0 || clicked12 > 0 ) {
                clicked07 = 0
            }
        }, 7000)
        Handler().postDelayed({
            iv31.foreground = null
            iv32.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv031.visibility = View.INVISIBLE
            if(iv32.visibility == View.INVISIBLE) {
                iv032.visibility = View.VISIBLE
            }
            layoutRR.setOnClickListener {
                clicked08++
                if (clicked08 == 1 && iv32.visibility == View.VISIBLE) {
                    iv32.callOnClick()
                }}
            if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                clicked07 > 0 || clicked09 > 0 || clicked10 > 0 ||
                clicked11 > 0 || clicked12 > 0 ) {
                clicked08 = 0
            }
        }, 8000)
        Handler().postDelayed({
            iv32.foreground = null
            iv33.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv032.visibility = View.INVISIBLE
            if(iv33.visibility == View.INVISIBLE) {
                iv033.visibility = View.VISIBLE
            }
            layoutRR.setOnClickListener {
                clicked09++
                if (clicked09 == 1 && iv33.visibility == View.VISIBLE) {
                    iv33.callOnClick()
                }}
            if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                clicked07 > 0 || clicked08 > 0 || clicked10 > 0 ||
                clicked11 > 0 || clicked12 > 0 ) {
                clicked09 = 0
            }
        }, 9000)
        Handler().postDelayed({
            iv33.foreground = null
            iv41.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv033.visibility = View.INVISIBLE
            if(iv41.visibility == View.INVISIBLE) {
                iv041.visibility = View.VISIBLE
            }
            layoutRR.setOnClickListener {
                clicked10++
                if (clicked10 == 1 && iv41.visibility == View.VISIBLE) {
                    iv41.callOnClick()
                }}
            if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                clicked07 > 0 || clicked08 > 0 || clicked09 > 0 ||
                clicked11 > 0 || clicked12 > 0 ) {
                clicked10 = 0
            }
        }, 10000)
        Handler().postDelayed({
            iv41.foreground = null
            iv42.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv041.visibility = View.INVISIBLE
            if(iv42.visibility == View.INVISIBLE) {
                iv042.visibility = View.VISIBLE
            }
            layoutRR.setOnClickListener {
                clicked11++
                if (clicked11 == 1 && iv42.visibility == View.VISIBLE) {
                    iv42.callOnClick()
                }}
            if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                clicked07 > 0 || clicked08 > 0 || clicked09 > 0 ||
                clicked10 > 0 || clicked12 > 0 ) {
                clicked11 = 0
            }
        }, 11000)
        Handler().postDelayed({
            iv42.foreground = null
            iv43.foreground = ContextCompat.getDrawable(applicationContext, R.drawable.ic_scissor)
            iv042.visibility = View.INVISIBLE
            if(iv43.visibility == View.INVISIBLE) {
                iv043.visibility = View.VISIBLE
            }
            layoutRR.setOnClickListener {
                clicked12++
                if (clicked12 == 1 && iv43.visibility == View.VISIBLE) {
                    iv43.callOnClick()
                }}
            if (clicked01 > 0 || clicked02 > 0 || clicked03 > 0 ||
                clicked04 > 0 || clicked05 > 0 || clicked06 > 0 ||
                clicked07 > 0 || clicked08 > 0 || clicked09 > 0 ||
                clicked10 > 0 || clicked11 > 0 ) {
                clicked12 = 0
            }
        }, 12000)



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

        //Check which image is selected and save it temporarily
        if (cardNumber == 1) {
            firstCard = cardsArray[card]
            if (firstCard > 200) {
                firstCard -= 100
            }
            cardNumber = 2
            clickedFirst = card

            iv.isEnabled = false

        } else if (cardNumber == 2) {
            secondCard = cardsArray[card]
            if (secondCard > 200) {
                secondCard -= 100
            }
            cardNumber = 1
            clickedSecond = card

            layoutRR.isEnabled = false


            val handler = Handler()
            handler.postDelayed({
                //Check if the images that are selected are equal
                calculate()
            }, 1000)

        }
    }

    @AnnotationSuppressLint("SetTextI18n")
    private fun calculate() {
        //If Images are equal remove them otherwise add Mismatch point
        if (firstCard == secondCard) {
            when (clickedFirst) {
                0 -> iv11.visibility = View.INVISIBLE
                1 -> iv12.visibility = View.INVISIBLE
                2 -> iv13.visibility = View.INVISIBLE
                3 -> iv21.visibility = View.INVISIBLE
                4 -> iv22.visibility = View.INVISIBLE
                5 -> iv23.visibility = View.INVISIBLE
                6 -> iv31.visibility = View.INVISIBLE
                7 -> iv32.visibility = View.INVISIBLE
                8 -> iv33.visibility = View.INVISIBLE
                9 -> iv41.visibility = View.INVISIBLE
                10 -> iv42.visibility = View.INVISIBLE
                11 -> iv43.visibility = View.INVISIBLE
            }

            when (clickedSecond) {
                0 -> iv11.visibility = View.INVISIBLE
                1 -> iv12.visibility = View.INVISIBLE
                2 -> iv13.visibility = View.INVISIBLE
                3 -> iv21.visibility = View.INVISIBLE
                4 -> iv22.visibility = View.INVISIBLE
                5 -> iv23.visibility = View.INVISIBLE
                6 -> iv31.visibility = View.INVISIBLE
                7 -> iv32.visibility = View.INVISIBLE
                8 -> iv33.visibility = View.INVISIBLE
                9 -> iv41.visibility = View.INVISIBLE
                10 -> iv42.visibility = View.INVISIBLE
                11 -> iv43.visibility = View.INVISIBLE
            }


        } else {
            mismatchPoints++
            tvp1.text = "Mismatch: $mismatchPoints"

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

        layoutRR.isEnabled = true

        //Check if the Game is Over
        checkEnd()
    }

    private fun checkEnd() {
        if (iv11.visibility == View.INVISIBLE &&
            iv12.visibility == View.INVISIBLE &&
            iv13.visibility == View.INVISIBLE &&
            iv21.visibility == View.INVISIBLE &&
            iv22.visibility == View.INVISIBLE &&
            iv23.visibility == View.INVISIBLE &&
            iv31.visibility == View.INVISIBLE &&
            iv32.visibility == View.INVISIBLE &&
            iv33.visibility == View.INVISIBLE &&
            iv41.visibility == View.INVISIBLE &&
            iv42.visibility == View.INVISIBLE &&
            iv43.visibility == View.INVISIBLE)

        {
            val alertDialogBuilder = AlertDialog.Builder(this@MemoryActivity)
            alertDialogBuilder
                .setMessage("GAME OVER!\nMismatch: $mismatchPoints")
                .setCancelable(false)
                .setPositiveButton("RESTART") { _, _ ->
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("EXIT") { _, _ -> finish()
                    stopService(Intent(this, MusicService::class.java))
                }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
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

