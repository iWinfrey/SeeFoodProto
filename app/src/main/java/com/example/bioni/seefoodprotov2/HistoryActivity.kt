package com.example.bioni.seefoodprotov2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout

import kotlinx.android.synthetic.main.activity_history.*
import java.io.File

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)

        val intent = intent
        val imagePaths: ArrayList<String>
        imagePaths = intent.getStringArrayListExtra("imagePaths")
        val layout = findViewById<RelativeLayout>(R.id.HistoryActivity)
        for (imagePath in imagePaths) {
            val image = ImageView(this)
            image.setImageURI(Uri.parse(imagePath))
            image.maxHeight = 50
            image.maxWidth = 50
            layout.addView(image)
        }
    }

    // TODO: implement this whole thing, show historic classifications, allow user to download image maybe?
    // TODO: could put statistics on this page, or could put them on main activity

}
