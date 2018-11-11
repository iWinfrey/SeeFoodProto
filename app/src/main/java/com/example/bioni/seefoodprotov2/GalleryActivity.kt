package com.example.bioni.seefoodprotov2

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

@Deprecated("Activity no longer required. Gallery app is opened directly from MainActivity, then image passed to UploadActivity")
class GalleryActivity : AppCompatActivity() {

    private val READ_REQUEST_CODE: Int = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        val selectPictureButton = findViewById<Button>(R.id.get_gallery_picture_btn)
        selectPictureButton.setOnClickListener {
            performFileSearch()
        }

    }

    fun performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            // Filter to only show results that can be "opened", such as a
            // file (as opposed to a list of contacts or timezones)
            addCategory(Intent.CATEGORY_OPENABLE)

            // Filter to show only images, using the image MIME data type.
            // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
            // To search for all documents available via installed storage providers,
            // it would be "*/*".
            type = "image/*"
        }

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            resultData?.data?.also { uri ->
                println(uri)

                val intent = Intent(this, UploadActivity::class.java).apply {
                    // pass the image data to the upload activity
                    putExtra("path", uri.path) // what does this do? nobody knows
                    putExtra("uri_string", uri.toString())
                }
                startActivity(intent)
            }
        }
    }
}
