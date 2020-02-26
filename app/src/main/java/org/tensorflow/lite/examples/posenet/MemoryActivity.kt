package org.tensorflow.lite.examples.posenet

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Button
import android.content.Intent
import android.annotation.SuppressLint as AnnotationSuppressLint

class MemoryActivity : AppCompatActivity() {

        private lateinit var tvp1: TextView
        private lateinit var btn1: Button

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

        //Array for images
        private var cardsArray = listOf(101, 102, 103, 104, 105, 106, 201, 202, 203, 204, 205, 206)

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

        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.activity_memory)

                //Close the app
                btn1 = findViewById(R.id.btn1)
                btn1.setOnClickListener {

                    val alertDialogBuilder = AlertDialog.Builder(this@MemoryActivity)
                    alertDialogBuilder
                        .setMessage("Are you sure?")
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


            iv11.setOnClickListener { view ->
                        val theCard = Integer.parseInt(view.tag as String)
                        doStuff(iv11, theCard)
                }

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

                        val handler = Handler()
                        handler.postDelayed({
                                //Check if the images that are selected are equal
                                calculate()
                        }, 1000)

                }
        }

        @AnnotationSuppressLint("SetTextI18n")
        private fun calculate() {
                //If Images are equal remove them otherwise add mismatch point
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

                        iv11.setImageResource(R.drawable.custom_ic_question)
                        iv12.setImageResource(R.drawable.custom_ic_question)
                        iv13.setImageResource(R.drawable.custom_ic_question)
                        iv21.setImageResource(R.drawable.custom_ic_question)
                        iv22.setImageResource(R.drawable.custom_ic_question)
                        iv23.setImageResource(R.drawable.custom_ic_question)
                        iv31.setImageResource(R.drawable.custom_ic_question)
                        iv32.setImageResource(R.drawable.custom_ic_question)
                        iv33.setImageResource(R.drawable.custom_ic_question)
                        iv41.setImageResource(R.drawable.custom_ic_question)
                        iv42.setImageResource(R.drawable.custom_ic_question)
                        iv43.setImageResource(R.drawable.custom_ic_question)

                }

                iv11.isEnabled = true
                iv12.isEnabled = true
                iv13.isEnabled = true
                iv21.isEnabled = true
                iv22.isEnabled = true
                iv23.isEnabled = true
                iv31.isEnabled = true
                iv32.isEnabled = true
                iv33.isEnabled = true
                iv41.isEnabled = true
                iv42.isEnabled = true
                iv43.isEnabled = true

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
                                .setPositiveButton("Try Again") { _, _ ->
                                        val intent = Intent(applicationContext, MemeoryActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                }
                                .setNegativeButton("Exit") { _, _ -> finish()
                                    stopService(Intent(this, MusicService::class.java))
                                }
                        val alertDialog = alertDialogBuilder.create()
                        alertDialog.show()
                }
        }

        private fun frontOfCardsResources() {

                image101 = R.drawable.custom_ic_img101
                image102 = R.drawable.custom_ic_img102
                image103 = R.drawable.custom_ic_img103
                image104 = R.drawable.custom_ic_img104
                image105 = R.drawable.custom_ic_img105
                image106 = R.drawable.custom_ic_img106
                image201 = R.drawable.custom_ic_img201
                image202 = R.drawable.custom_ic_img202
                image203 = R.drawable.custom_ic_img203
                image204 = R.drawable.custom_ic_img204
                image205 = R.drawable.custom_ic_img205
                image206 = R.drawable.custom_ic_img206


        }

}

