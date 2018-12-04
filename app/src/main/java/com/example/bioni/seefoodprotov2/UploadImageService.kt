package com.example.bioni.seefoodprotov2

import android.app.IntentService
import android.content.Intent
import android.util.Log
import okhttp3.*
import org.json.JSONArray
import java.io.File

// https://developer.android.com/training/run-background-service/create-service
// https://developer.android.com/guide/components/services#ExtendingIntentService

class UploadImageService : IntentService(UploadImageService::class.simpleName) {

    override fun onHandleIntent(workIntent: Intent) {

        val client = OkHttpClient()

        for (i in 0 until CandidateImage.file!!.size) {
            val image = CandidateImage.file!![i]
            val requestBody2 = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("the_file", image.name, RequestBody.create(MediaType.parse("application/json"), image))
                .build()

            val request = Request.Builder()

                .url("http://3.16.73.99/upload")
//            .url("http://192.168.0.200:5000/upload") // for local testing (insert your computer's local IP here)
                .addHeader("Content-Type", "multipart/form-data")
                .post(requestBody2)
                .build()


            val response = client.newCall(request).execute()
            CandidateImage.classification?.add(response.body()!!.string())
        }
        Intent(this, ResultsActivity::class.java).also { intent ->
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

    }

}