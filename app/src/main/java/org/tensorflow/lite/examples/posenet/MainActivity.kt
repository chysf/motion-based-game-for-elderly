package org.tensorflow.lite.examples.posenet

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import org.tensorflow.lite.examples.posenet.PosenetActivity.Companion.gameID

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val clickListener = View.OnClickListener { view ->
            when(view.id){
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
                    val intent = Intent(this, HelloWorld::class.java)
                    gameID = 4
                    startActivity(intent)
                }
            }
        }
        val motionGameBtn: Button = findViewById(R.id.motionGameBtn)
        motionGameBtn.setOnClickListener(clickListener)
        val eatingGameBtn: Button = findViewById(R.id.eatingGameBtn)
        eatingGameBtn.setOnClickListener(clickListener)
        val memoryGameBtn: Button = findViewById(R.id.memoryGameBtn)
        memoryGameBtn.setOnClickListener(clickListener)
        val testBtn: Button = findViewById(R.id.testBtn)
        testBtn.setOnClickListener(clickListener)

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