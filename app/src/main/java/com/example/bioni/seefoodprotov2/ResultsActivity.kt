package com.example.bioni.seefoodprotov2

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        var previewBox = findViewById<ImageView>(R.id.image_preview2)
        previewBox.setImageURI(Uri.fromFile(CandidateImage.file))

        var classificationText = findViewById<TextView>(R.id.classification_text)
        val resultText = "Is it food?... " + CandidateImage.classification
        classificationText.text = resultText

        val openMainActivity = findViewById<Button>(R.id.go_home_btn)
        openMainActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("Thing to do", "Going back to main activity!") // what does this do? nobody knows
            }
            startActivity(intent)
        }

        if (CandidateImage.source == "gallery") {
            // set actions for the navigation buttons
            val goBackButton = findViewById<Button>(R.id.go_back_btn2)
            goBackButton.text = "Select another pic"
            goBackButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("do_thing", "gallery") // what does this do? nobody knows
                }
                startActivity(intent)
            }
        } else {
            // set actions for the navigation buttons
            val goBackButton = findViewById<Button>(R.id.go_back_btn2)
            goBackButton.text = "Take another pic"
            goBackButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("do_thing", "camera") // what does this do? nobody knows
                }
                startActivity(intent)
            }
        }
    }

    // TODO: allow the user to go back to MainActivity at least, at most allow them to open camera or gallery app again, or go to history
}
