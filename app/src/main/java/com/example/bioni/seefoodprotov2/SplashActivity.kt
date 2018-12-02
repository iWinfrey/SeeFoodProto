package com.example.bioni.seefoodprotov2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.support.annotation.Nullable
import android.os.Handler


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //Loading/Splash Screen

        val SPLASH_DISPLAY_LENGTH = 5000                   //About 2-3 seconds

        Handler().postDelayed({
            run() {
                val intent = Intent(this, MainActivity::class.java)         //Will lead into the main screen
                startActivity(intent)                                                   //Start next activity
                finish()                                                                    //Finished loading screen
            }
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}
