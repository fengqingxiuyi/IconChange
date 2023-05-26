package com.fqxyi.iconchange

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

//测试页
class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  fun resetChange(view: View) {
    IconChangeManager.changeIcon(this, 0, false)
  }

  fun iconChange(view: View) {
    IconChangeManager.changeIcon(this, 1, false)
  }

  fun resetChangeKill(view: View) {
    IconChangeManager.changeIcon(this, 0, true)
  }

  fun iconChangeKill(view: View) {
    IconChangeManager.changeIcon(this, 1, true)
  }
}