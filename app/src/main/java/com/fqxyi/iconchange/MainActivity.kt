package com.fqxyi.iconchange

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  fun resetChange(view: View) {
    IconChangeManager.changeIcon(this, 0)
  }

  fun iconChange(view: View) {
    IconChangeManager.changeIcon(this, 1)
  }
}