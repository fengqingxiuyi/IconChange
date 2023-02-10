package com.fqxyi.iconchange

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

  private val monitor = AppSwitchMonitor()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)
//    application.registerActivityLifecycleCallbacks(monitor)
    Handler().postDelayed({
      startActivity(Intent(this, MainActivity::class.java))
    }, 1000)
  }

  override fun onDestroy() {
    super.onDestroy()
//    application.unregisterActivityLifecycleCallbacks(monitor)
  }
}