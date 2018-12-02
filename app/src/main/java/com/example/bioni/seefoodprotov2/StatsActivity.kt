package com.example.bioni.seefoodprotov2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.example.bioni.seefoodprotov2.R.id.toolbar
import android.R.attr.button
import android.view.Gravity
import android.R.attr.gravity
import android.app.ActionBar
import android.content.Intent
import android.view.ViewGroup.LayoutParams.FILL_PARENT
import android.widget.Button
import android.widget.LinearLayout



class StatsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bt = Button(this)
        bt.setText("Refresh")
        toolbar.addView(bt)

        /*openHistoryButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra("Thing to do", "Start Historying!") // what does this do? nobody knows
            }
            startActivity(intent)
        }*/
    }
}
