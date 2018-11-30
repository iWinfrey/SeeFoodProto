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

        if (CandidateImage.source == "gallery") {
            // set actions for the navigation buttons
            val goBackButton = findViewById<Button>(R.id.go_back_btn)
            goBackButton.text = "Select a different pic"
            goBackButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("do_thing", "gallery") // what does this do? nobody knows
                }
                startActivity(intent)
            }
        } else {
            // set actions for the navigation buttons
            val goBackButton = findViewById<Button>(R.id.go_back_btn)
            goBackButton.text = "Take a different pic"
            goBackButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("do_thing", "camera") // what does this do? nobody knows
                }
                startActivity(intent)
            }
        }



    }
}
