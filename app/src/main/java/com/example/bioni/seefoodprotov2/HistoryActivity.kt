package com.example.bioni.seefoodprotov2

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.GridView
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


class HistoryActivity : AppCompatActivity() {
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private lateinit var didStuff: Deferred<Unit>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        setSupportActionBar(toolbar)
        didStuff = doStuff()
    }

    // TODO: implement this whole thing, show historic classifications, allow user to download image maybe?
    // TODO: could put statistics on this page, or could put them on main activity
    private fun doStuff() = uiScope.async {
        val client = OkHttpClient()

        val request = Request.Builder()

            .url("http://3.16.73.99/upload")
//            .url("http://192.168.0.200:5000/upload") // for local testing (insert your computer's local IP here)
            .addHeader("Content-Type", "application/zip")
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
        val imagePaths = ArrayList<String>()
        val job = GlobalScope.launch {
            val response = client.newCall(request).execute()
            val responseHeaders = response.headers()
            for (i in 0 until responseHeaders.size()) {
                Log.d(responseHeaders.name(i), responseHeaders.value(i))
            }
            Log.d("Body", response.body().toString())

            // Download the ZIP
            val byteArray = response.body()?.bytes()
            val root = Environment.getExternalStorageDirectory().toString()
            val file = File("$root/Download/test.zip")
            if (file.exists())
                file.delete()
            try {
                val out = FileOutputStream(file)
                out.write(byteArray)
                out.flush()
                out.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Extract the ZIP
            val path = "$root/Download/"
            val `is`: InputStream
            val zis: ZipInputStream
            try {
                var filename: String
                `is` = FileInputStream(path + "test.zip")
                zis = ZipInputStream(BufferedInputStream(`is`))
                var ze: ZipEntry?
                val buffer = ByteArray(1024)
                var count: Int
                ze = zis.nextEntry
                while (ze != null) {
                    filename = ze.name

                    // Need to create directories if not exists, or
                    // it will generate an Exception...
                    if (ze.isDirectory) {
                        val fmd = File(path + filename)
                        fmd.mkdirs()
                        ze = zis.nextEntry
                        continue
                    }

                    val fout = FileOutputStream(path + filename)
                    imagePaths.add(path + filename)
                    count = zis.read(buffer)
                    while (count != -1) {
                        fout.write(buffer, 0, count)
                        count = zis.read(buffer)
                    }

                    fout.close()
                    zis.closeEntry()
                    ze = zis.nextEntry
                }

                zis.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        job.join() // Wait for the dang thread to finish before actually trying to show the images

        //val layout = findViewById<RelativeLayout>(R.id.HistoryActivity)
        val data = ArrayList<GridImage>()
        val gridView = findViewById<GridView>(R.id.gridView)
        for (imagePath in imagePaths) {
            val imageName = File(imagePath).nameWithoutExtension
            val imageScore = imageName.substring(0, imageName.length - 7)
            val strArray = imageScore.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val builder = StringBuilder()
            for (s in strArray) {
                val cap = s.substring(0, 1).toUpperCase() + s.substring(1)
                builder.append("$cap ")
            }
            data.add(GridImage(builder.toString(), imagePath))
//            val image = ImageView(applicationContext)
//            image.setImageURI(Uri.parse(imagePath))
//            image.maxHeight = 50
//            image.maxWidth = 50
//            layout.addView(image)
        }

        gridView.adapter = GridViewAdapter(applicationContext, data)
    }
}
