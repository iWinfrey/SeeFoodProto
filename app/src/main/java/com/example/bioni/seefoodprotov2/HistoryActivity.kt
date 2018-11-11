package com.example.bioni.seefoodprotov2

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;

import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
    }

    // TODO: implement this whole thing, show historic classifications, allow user to download image maybe?
    // TODO: could put statistics on this page, or could put them on main activity

}
