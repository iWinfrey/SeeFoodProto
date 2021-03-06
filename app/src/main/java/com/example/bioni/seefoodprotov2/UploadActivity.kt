package com.example.bioni.seefoodprotov2

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast

class UploadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        val data = ArrayList<GridImage>()
        val gridView = findViewById<GridView>(R.id.gridView)
        for (i in 0 until (CandidateImage.file?.size as Int)) {
            val path = CandidateImage.file!![i].path
            data.add(GridImage("Potato", path))
        }
        gridView.adapter = GridViewAdapter(applicationContext, data, "Upload")

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
            //goBackButton.text = "Select different picture"
            goBackButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("do_thing", "gallery") // what does this do? nobody knows
                }
                startActivity(intent)
            }
        } else {
            // set actions for the navigation buttons
            val goBackButton = findViewById<Button>(R.id.go_back_btn)
            //goBackButton.text = "Take new picture"
            goBackButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("do_thing", "camera") // what does this do? nobody knows
                }
                startActivity(intent)
            }
        }



    }
}
