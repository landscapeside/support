package com.landside.support

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.landside.support.extensions.dialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dialog(this){
            
        }
    }
}