package com.example.bioni.seefoodprotov2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.ImageView
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.R.transition.fade
import java.util.Random


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    var flag = false



    fun fade(view: View) {
        val image = findViewById<ImageView>(R.id.image)
        val animation1 = AnimationUtils.loadAnimation(applicationContext,R.anim.fade)
        image.startAnimation(animation1)
    }

    fun changeImage(v: View) {
        val iv = findViewById<ImageView>(R.id.image)
        //use flag to change image
        val random = Random()
        fun rand(from: Int, to: Int) : Int {
            return random.nextInt(to-from) + from
        }

        val rando = rand(0,5)

        if (rando == 0) {
            fade(v)                                             //fade animation
            iv.setImageResource(R.drawable.wolfpic)             //first wolf pic

        } else if (rando == 1) {
            fade(v)
            iv.setImageResource(R.drawable.wolfpic2)

        }
        else if (rando == 2){
            fade(v)
            iv.setImageResource(R.drawable.wolfpic7)
        }

        else if (rando == 3) {
            fade(v)
            iv.setImageResource(R.drawable.wolfpic3)
        }
        else if (rando == 4) {
            fade(v)
            iv.setImageResource(R.drawable.wolfpic6)
        }
        else {
            fade(v)
            iv.setImageResource(R.drawable.wolfpic4)
        }
    }
}
