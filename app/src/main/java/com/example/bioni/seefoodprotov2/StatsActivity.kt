package com.example.bioni.seefoodprotov2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject


class StatsActivity : AppCompatActivity() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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

        doStuff()
    }

    private fun doStuff() = uiScope.async {
        val client = OkHttpClient()

        val request = Request.Builder()

            .url("http://3.16.73.99/stats")
//            .url("http://192.168.0.200:5000/upload") // for local testing (insert your computer's local IP here)
            .addHeader("Content-Type", "application/json")
            .build()

        /* This trick is insane and I don't know why or how it works.
         * Supposedly a coroutine (async thread) can have its scope set
         * If you notice, doStuff() is set to the uiScope, which is the same scope as main
         * Therefore, anything inside can edit the layout
         * BUT, GlobalScope cannot
         * However, it can set variables outside of it's thread/scope
         * Which freaks me out, but it works.
         * Supah fast threading!
         */
        var jsonData = JSONObject()
        val job = GlobalScope.launch {
            val response = client.newCall(request).execute()
            val responseHeaders = response.headers()
            for (i in 0 until responseHeaders.size()) {
                Log.d(responseHeaders.name(i), responseHeaders.value(i))
            }
            //Log.d("Body", response.body()?.string())

            jsonData = JSONObject(response.body()?.string())
        }
        job.join() // Wait for the dang thread to finish before actually trying to show the images

        val totYear = findViewById<TextView>(R.id.textView7)
        val totToday = findViewById<TextView>(R.id.textView6)
        val totAll = findViewById<TextView>(R.id.textView5)
        val totFood = findViewById<TextView>(R.id.textView9)
        val totNotFood = findViewById<TextView>(R.id.textView10)
        totYear.text = jsonData.getString("numAllTime")
        totToday.text = jsonData.getString("numToday")
        totAll.text = jsonData.getString("numAllTime")
        totFood.text = jsonData.getString("numYesAllTime")
        totNotFood.text = jsonData.getString("numNoAllTime")
    }
}
