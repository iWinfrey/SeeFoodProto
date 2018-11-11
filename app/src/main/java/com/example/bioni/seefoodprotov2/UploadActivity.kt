package com.example.bioni.seefoodprotov2

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import java.io.File

class UploadActivity : AppCompatActivity() {

    lateinit var imagePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        imagePath = intent.getStringExtra("path")
        println(imagePath)

        var previewBox = findViewById<ImageView>(R.id.image_preview)
        var uri = Uri.fromFile(File(imagePath))
        previewBox.setImageURI(uri)

    }
}
