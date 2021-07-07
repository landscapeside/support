package com.landside.support

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.landside.support.extensions.popAsDown
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.window_first.*

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    click_me.setOnClickListener {
      click_me.popAsDown(R.layout.window_first){window->
        open_second.setOnClickListener {
          window.dismiss()
        }
      }
    }
  }
}