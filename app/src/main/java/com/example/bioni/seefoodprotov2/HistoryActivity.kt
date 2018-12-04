package com.example.bioni.seefoodprotov2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.Button
import android.widget.GridView
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import org.json.JSONObject


class HistoryActivity : AppCompatActivity() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var didStuff: Deferred<Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
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

        val btnRefresh= Button(this)
        btnRefresh.setText("Refresh")
        btnRefresh.setTextSize(18F)
        btnRefresh.setBackgroundColor(Color.parseColor("#4b0082"))
        btnRefresh.setTextColor(Color.WHITE)
        toolbar.addView(btnRefresh)

        btnRefresh.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra("Thing to do", "History") // what does this do? nobody knows
            }
            startActivity(intent)
        }
        //setSupportActionBar(toolbar)
        didStuff = doStuff()
    }

    // TODO: implement this whole thing, show historic classifications, allow user to download image maybe?
    // TODO: could put statistics on this page, or could put them on main activity
    private fun doStuff() = uiScope.async {
        val client = OkHttpClient()

        val request = Request.Builder()

            .url("http://3.16.73.99/upload")
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
        var jsonData = JSONArray()
        val job = GlobalScope.launch {
            val response = client.newCall(request).execute()
            val responseHeaders = response.headers()
            for (i in 0 until responseHeaders.size()) {
                Log.d(responseHeaders.name(i), responseHeaders.value(i))
            }
            //Log.d("Body", response.body()?.string())

            jsonData = JSONArray(response.body()?.string())
        }
        job.join() // Wait for the dang thread to finish before actually trying to show the images

        val data = ArrayList<GridImage>()
        val gridView = findViewById<GridView>(R.id.gridView)
        for (i in 0 until jsonData.length()) {
            val imageData = jsonData.getJSONObject(i)
            val path = imageData.getString("path")
            val score = imageData.getString("score")
            data.add(GridImage(score, path))
        }
        gridView.adapter = GridViewAdapter(applicationContext, data, "History")
    }
}
