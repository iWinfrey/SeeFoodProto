package com.example.bioni.seefoodprotov2

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.example.bioni.seefoodprotov2.R.id.textView

class ResultsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_results)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val btnHome = Button(this)
        btnHome.setText("Home")
        btnHome.setBackgroundColor(Color.parseColor("#4b0082"))
        btnHome.setTextColor(Color.WHITE)
        toolbar.addView(btnHome)

        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("Thing to do", "Go Home") // what does this do? nobody knows
            }
            startActivity(intent)
        }

        val btnHistory = Button(this)
        btnHistory.setText("History")
        btnHistory.setBackgroundColor(Color.parseColor("#4b0082"))
        btnHistory.setTextColor(Color.WHITE)
        toolbar.addView(btnHistory)

        btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra("Thing to do", "History") // what does this do? nobody knows
            }
            startActivity(intent)
        }

        val btnStats = Button(this)
        btnStats.setText("Stats")
        btnStats.setBackgroundColor(Color.parseColor("#4b0082"))
        btnStats.setTextColor(Color.WHITE)
        toolbar.addView(btnStats)

        btnStats.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java).apply {
                putExtra("Thing to do", "Stats") // what does this do? nobody knows
            }
            startActivity(intent)
        }

        /*val openMainActivity = findViewById<Button>(R.id.go_home_btn)
        openMainActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtra("Thing to do", "Going back to main activity!") // what does this do? nobody knows
            }
            startActivity(intent)
        }*/

        var previewBox = findViewById<ImageView>(R.id.image_preview2)
        previewBox.setImageURI(Uri.fromFile(CandidateImage.file))

        var classificationText = findViewById<TextView>(R.id.classification_text)
        val resultText = "Is it food?... " + CandidateImage.classification
        classificationText.text = resultText

        val progress1 = findViewById<View>(R.id.progress_1) as RoundCornerProgressBar
        progress1.setProgressBackgroundColor(Color.parseColor("#ffffff"))
        progress1.setMax(60F)

        if(resultText.equals("Is it food?... High No")){
            progress1.setProgressColor(Color.parseColor("#b20000"))
            progress1.setProgress(10F)
        } else if(resultText.equals("Is it food?... Moderate No")){
            progress1.setProgressColor(Color.parseColor("#ff4500"))
            progress1.setProgress(20F)
        } else if(resultText.equals("Is it food?... Low No")){
            progress1.setProgressColor(Color.parseColor("#ffff33"))
            progress1.setProgress(30F)
        } else if(resultText.equals("Is it food?... Low Yes")){
            progress1.setProgressColor(Color.parseColor("#228B22"))
            progress1.setProgress(40F)
        } else if(resultText.equals("Is it food?... Moderate Yes")){
            progress1.setProgressColor(Color.parseColor("#3232cd"))
            progress1.setProgress(50F)
        } else if(resultText.equals("Is it food?... High Yes")){
            progress1.setProgressColor(Color.parseColor("##4b0082"))
            progress1.setProgress(60F)
        }

        if (CandidateImage.source == "gallery") {
            // set actions for the navigation buttons
            val goBackButton = findViewById<Button>(R.id.go_back_btn2)
            goBackButton.text = "Select another picture"
            goBackButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("do_thing", "gallery") // what does this do? nobody knows
                }
                startActivity(intent)
            }
        } else {
            // set actions for the navigation buttons
            val goBackButton = findViewById<Button>(R.id.go_back_btn2)
            goBackButton.text = "Take another picture"
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
