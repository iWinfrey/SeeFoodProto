package com.example.bioni.seefoodprotov2

import android.app.IntentService
import android.content.Intent
import okhttp3.*

// https://developer.android.com/training/run-background-service/create-service
// https://developer.android.com/guide/components/services#ExtendingIntentService

class UploadImageService : IntentService(UploadImageService::class.simpleName) {

    override fun onHandleIntent(workIntent: Intent) {

        // Gets data from the incoming Intent
        val dataString = workIntent.dataString

        val client = OkHttpClient()

        val requestBody2 = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("the_file", CandidateImage.file?.name, RequestBody.create(MediaType.parse("application/json"), CandidateImage.file))
            .build()

        val request = Request.Builder()

            .url("http://3.16.73.99/upload")
//            .url("http://192.168.0.200:5000/upload") // for local testing (insert your computer's local IP here)
            .addHeader("Content-Type", "multipart/form-data")
            .post(requestBody2)
            .build()


        val response = client.newCall(request).execute()

        CandidateImage.classification = response.body()?.string()

        Intent(this, ResultsActivity::class.java).also { intent ->
            startActivity(intent)
        }

    }
}