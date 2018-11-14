package com.example.bioni.seefoodprotov2

import android.app.IntentService
import android.content.Intent
import android.widget.Toast
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject

// https://developer.android.com/training/run-background-service/create-service
// https://developer.android.com/guide/components/services#ExtendingIntentService

class UploadImageService : IntentService(UploadImageService::class.simpleName) {

    override fun onHandleIntent(workIntent: Intent) {
        // Gets data from the incoming Intent
        val dataString = workIntent.dataString
        // Do work here, based on the contents of dataString

        val client = OkHttpClient()

        print("trying upload...")

        Toast.makeText(this, "It's working", Toast.LENGTH_SHORT).show()

        var obj = JSONObject()
        obj.put("the_file", FileUploadCandidate.file?.readBytes())

        // TODO: get this working
        val request = Request.Builder()
            .url("http://3.16.73.99/upload")
//            .url("http://127.0.0.1:5000/upload")
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(MediaType.parse("application/json"), obj.toString()))
//            .post(RequestBody.create(MediaType.parse("application/json"), "{}"))
            .build()

        val response = client.newCall(request).execute()

        print(response.body().toString())

        // TODO: launch the results activity after the call comes back

        // TODO: Do we have to manually kill this thing after we're done with it?

    }
}