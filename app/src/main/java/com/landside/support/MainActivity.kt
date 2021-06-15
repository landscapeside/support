package com.landside.support

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.landside.support.extensions.dialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    click_me.setOnClickListener {
      FirstWindow(this, click_me).showAsDropDown(container)
    }
  }
}