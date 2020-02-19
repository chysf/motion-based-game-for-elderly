package org.tensorflow.lite.examples.posenet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val clickListener = View.OnClickListener { view ->
            when(view.getId()){
                R.id.motionGameBtn -> {
                    val intent = Intent(this, CameraActivity::class.java)
                    // start your next activity
                    startActivity(intent)
                }
                R.id.eatingGameBtn -> {
                    val intent2 = Intent(this, EatingActivity::class.java)
                    // start your next activity
                    startActivity(intent2)
                }
            }
        }
        val motionGameBtn: Button = findViewById(R.id.motionGameBtn)
        motionGameBtn.setOnClickListener(clickListener)
        val eatingGameBtn: Button = findViewById(R.id.eatingGameBtn)
        eatingGameBtn.setOnClickListener(clickListener)

    }

}