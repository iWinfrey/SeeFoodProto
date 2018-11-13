package com.example.bioni.seefoodprotov2

import android.app.IntentService
import android.content.Intent
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

// https://developer.android.com/training/run-background-service/create-service

class UploadImageService : IntentService(UploadImageService::class.simpleName) {

    override fun onHandleIntent(workIntent: Intent) {
        // Gets data from the incoming Intent
        val dataString = workIntent.dataString
        // Do work here, based on the contents of dataString

        val client = OkHttpClient()

        print("trying upload...")

        Toast.makeText(this, "It's working", Toast.LENGTH_SHORT).show()

        // TODO: get this working
        val request = Request.Builder()
            .url("http://3.16.73.99/upload")
            .addHeader("message", "do we want to send something here?")
            .post(RequestBody.create(MediaType.parse("*/*"), FileUploadCandidate.file?.readBytes()))
            .build()

        val response = client.newCall(request).execute()

        print(response.body().toString())

    }
}