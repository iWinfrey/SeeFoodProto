package com.example.bioni.seefoodprotov2

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class UploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        // put the image in the preview box
        var previewBox = findViewById<ImageView>(R.id.image_preview)
        previewBox.setImageURI(Uri.fromFile(CandidateImage.file))

        // set actions for the navigation buttons
        val uploadImageButton = findViewById<Button>(R.id.upload_btn)
        uploadImageButton.setOnClickListener {

            Toast.makeText(this, "Uploading Image...", Toast.LENGTH_SHORT).show()

            // use of a service is required for network calls, because network calls are not allowed to execute on the main thread
            Intent(this, UploadImageService::class.java).also { intent ->
                startService(intent)
            }

        }

        // set actions for the navigation buttons
        val goBackButton = findViewById<Button>(R.id.go_back_btn)
        goBackButton.setOnClickListener {
            // TODO: either camera or gallery if the user doesn't like the picture
        }

    }
}
