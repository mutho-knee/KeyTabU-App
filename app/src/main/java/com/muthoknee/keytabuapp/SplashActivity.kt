package com.muthoknee.keytabuapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowInsets
import android.view.WindowManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        @Suppress("DEPRECIATION")
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.R){
            window.insetsController?.hide((WindowInsets.Type.statusBars()))
        }else{
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

        }
        @Suppress("DEPRECIATION")
        Handler().postDelayed(
            {
                // Launch the Login Activity
                startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
                finish()
            },
            2500
        )

    }
}




