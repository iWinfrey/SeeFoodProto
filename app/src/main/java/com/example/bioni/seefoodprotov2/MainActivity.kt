package com.example.bioni.seefoodprotov2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import java.util.Random


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ask for permissions upfront if they aren't granted
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            val writePermission = Array(1) { Manifest.permission.CAMERA}
            requestPermissions(writePermission, 16846846)
        }

        // set actions for the navigation buttons
        val openCameraButton = findViewById<Button>(R.id.open_camera_btn)
        openCameraButton.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java).apply {
                putExtra("Thing to do", "Start Cameraing!") // what does this do? nobody knows
            }
            startActivity(intent)
        }
        val openGalleryActivity= findViewById<Button>(R.id.open_gallery_btn)
        openGalleryActivity.setOnClickListener {
            val intent = Intent(this, GalleryActivity::class.java).apply {
                putExtra("Thing to do", "Start Gallerying!") // what does this do? nobody knows
            }
            startActivity(intent)
        }
        val openHistoryButton = findViewById<Button>(R.id.open_history_btn)
        openHistoryButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra("Thing to do", "Start Historying!") // what does this do? nobody knows
            }
            startActivity(intent)
        }

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
