package com.example.bioni.seefoodprotov2

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.GridView
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
        btnHome.setTextSize(18F)
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
        btnHistory.setTextSize(18F)
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
        btnStats.setTextSize(18F)
        btnStats.setBackgroundColor(Color.parseColor("#4b0082"))
        btnStats.setTextColor(Color.WHITE)
        toolbar.addView(btnStats)

        btnStats.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java).apply {
                putExtra("Thing to do", "Stats") // what does this do? nobody knows
            }
            startActivity(intent)
        }

        val data = ArrayList<GridImage>()
        val gridView = findViewById<GridView>(R.id.gridView)
        for (i in 0 until CandidateImage.file!!.size) {
            val path = CandidateImage.file!![i].path
            val score = CandidateImage.classification!![i]
            data.add(GridImage(score, path))
        }
        gridView.adapter = GridViewAdapter(applicationContext, data, "Results")

        if (CandidateImage.source == "gallery") {
            // set actions for the navigation buttons
            val goBackButton = findViewById<Button>(R.id.go_back_btn2)
            goBackButton.text = " Select another picture "
            goBackButton.setOnClickListener {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("do_thing", "gallery") // what does this do? nobody knows
                }
                startActivity(intent)
            }
        } else {
            // set actions for the navigation buttons
            val goBackButton = findViewById<Button>(R.id.go_back_btn2)
            goBackButton.text = " Take another picture "
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
