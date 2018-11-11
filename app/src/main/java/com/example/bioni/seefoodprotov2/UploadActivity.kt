package com.example.bioni.seefoodprotov2

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class UploadActivity : AppCompatActivity() {

    lateinit var imagePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        // put the image in the preview box
        var previewBox = findViewById<ImageView>(R.id.image_preview)
        previewBox.setImageURI(Uri.fromFile(FileUploadCandidate.file))

        // set actions for the navigation buttons
        val uploadImageButton = findViewById<Button>(R.id.open_camera_btn)
        uploadImageButton.setOnClickListener {
            // TODO: upload to server then open the results activity
        }

        // TODO: add a button to go back to either camera or gallery if the user doens't like the picture

    }
}
