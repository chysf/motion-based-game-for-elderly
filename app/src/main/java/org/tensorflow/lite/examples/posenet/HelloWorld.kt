package org.tensorflow.lite.examples.posenet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class HelloWorld: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.helloworld)
        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .replace(R.id.container, PosenetActivity())
            .commit()
    }
}