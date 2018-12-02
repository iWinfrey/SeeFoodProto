package com.example.bioni.seefoodprotov2

import android.Manifest
import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.util.Log
import okhttp3.*
import android.R.attr.name
import android.R.attr.path
import android.graphics.Bitmap
import android.os.Environment
import android.os.Environment.DIRECTORY_MOVIES
import android.os.Environment.getExternalStoragePublicDirectory
import android.widget.Toast
import java.io.*
import java.util.*
import java.nio.file.Files.isDirectory
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import kotlin.collections.ArrayList


// https://developer.android.com/training/run-background-service/create-service
// https://developer.android.com/guide/components/services#ExtendingIntentService

@SuppressLint("Registered")
class GetImagesService : IntentService(UploadImageService::class.simpleName) {

    override fun onHandleIntent(workIntent: Intent) {
        // Gets data from the incoming Intent
        val dataString = workIntent.dataString

        val client = OkHttpClient()

        val request = Request.Builder()

            .url("http://3.16.73.99/upload")
//            .url("http://192.168.0.200:5000/upload") // for local testing (insert your computer's local IP here)
            .addHeader("Content-Type", "application/zip")
            .build()


        val response = client.newCall(request).execute()
        val responseHeaders = response.headers()
        for (i in 0 until responseHeaders.size()) {
            println(responseHeaders.name(i) + ": " + responseHeaders.value(i))
        }

        Log.d("Test", response.body().toString())
        val zipPath = saveToExternalStorage(response.body()!!.bytes())
        val imagePaths = unpackZip()
        Intent(this, HistoryActivity::class.java).also { intent ->
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("imagePaths", imagePaths)
            startActivity(intent)
        }
    }

    private fun saveToExternalStorage(byteArray: ByteArray): String {
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
            val toast = Toast.makeText(this, "Zip couldn't be saved!", Toast.LENGTH_LONG)
            toast.show()
        }
        val toast = Toast.makeText(this, "Zip saved in $root", Toast.LENGTH_LONG)
            toast.show()
        return file.absolutePath
    }

    private fun unpackZip(): ArrayList<String> {
        val root = Environment.getExternalStorageDirectory().toString()
        val path = "$root/Download/"
        val `is`: InputStream
        val zis: ZipInputStream
        val imagePaths = ArrayList<String>()
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
            return imagePaths
        }
        return imagePaths
    }

}