package com.example.bioni.seefoodprotov2

import android.R.attr.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.bioni.seefoodprotov2.R.id.toolbar
import android.view.Gravity
import android.app.ActionBar
import android.content.Intent
import android.graphics.Color
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.widget.Button
import android.widget.LinearLayout
import okhttp3.Request


class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

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

        //Having a stats button just seems redundant if we have a refresh one
        /*val btnStats = Button(this)
        btnStats.setText("Stats")
        btnStats.setBackgroundColor(Color.parseColor("#4b0082"))
        btnStats.setTextColor(Color.WHITE)
        toolbar.addView(btnStats)

        btnStats.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java).apply {
                putExtra("Thing to do", "Stats") // what does this do? nobody knows
            }
            startActivity(intent)
        }*/

        val btnRefresh = Button(this)
        btnRefresh.setText("Refresh")
        btnRefresh.setTextSize(18F)
        btnRefresh.setBackgroundColor(Color.parseColor("#4b0082"))
        btnRefresh.setTextColor(Color.WHITE)
        toolbar.addView(btnRefresh)

        btnRefresh.setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java).apply {
                putExtra("Thing to do", "Refresh") // what does this do? nobody knows
            }
            startActivity(intent)
        }
    }

    private fun doStuff() {
        val request = Request.Builder()
            .url("http://3.16.73.99/upload")
//            .url("http://192.168.0.200:5000/upload") // for local testing (insert your computer's local IP here)
            .addHeader("Content-Type", "application/json")
            .build()


    }
}
