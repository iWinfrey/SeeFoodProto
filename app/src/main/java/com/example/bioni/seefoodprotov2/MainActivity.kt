package com.example.bioni.seefoodprotov2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.widget.ImageView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.provider.DocumentsContract


class MainActivity : AppCompatActivity() {

    private val REQUEST_FILE_PICKER: Int = 9843
    private val REQUEST_TAKE_PHOTO: Int = 4782

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check for read and write external storage permissions, if we haven't already been granted them
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val readPermission = Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE}
            requestPermissions(readPermission, 999)
        }
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val writePermission = Array(1) { Manifest.permission.WRITE_EXTERNAL_STORAGE}
            requestPermissions(writePermission, 9999)
        }

        // set actions for the navigation buttons
        val openCameraButton = findViewById<Button>(R.id.open_camera_btn)
        openCameraButton.setOnClickListener {

            // check for camera permissions, if we haven't already been granted them
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                val cameraPermission = Array(1) { Manifest.permission.CAMERA}
                requestPermissions(cameraPermission, 99)
            }

            // opens the camera directly
            dispatchTakePictureIntent()
        }

        val openGalleryActivity= findViewById<Button>(R.id.open_gallery_btn)
        openGalleryActivity.setOnClickListener {

            // opens the system file picker
            performFileSearch()
        }

        val openHistoryButton = findViewById<Button>(R.id.open_history_btn)
        openHistoryButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra("Thing to do", "Start Historying!") // what does this do? nobody knows
            }
            startActivity(intent)
        }

    }

    var flag = false


    fun fade(view: View) {
        val image = findViewById<ImageView>(R.id.image)
        val animation1 = AnimationUtils.loadAnimation(applicationContext,R.anim.fade)
        image.startAnimation(animation1)
    }

    fun changeImage(v: View) {
        val iv = findViewById<ImageView>(R.id.image)
        //use flag to change image
        val random = Random()
        fun rand(from: Int, to: Int) : Int {
            return random.nextInt(to-from) + from
        }

        val rando = rand(0,5)

        if (rando == 0) {
            fade(v)                                             //fade animation
            iv.setImageResource(R.drawable.wolfpic)             //first wolf pic
        } else if (rando == 1) {
            fade(v)
            iv.setImageResource(R.drawable.wolfpic2)
        }
        else if (rando == 2){
            fade(v)
            iv.setImageResource(R.drawable.wolfpic7)
        }
        else if (rando == 3) {
            fade(v)
            iv.setImageResource(R.drawable.wolfpic3)
        }
        else if (rando == 4) {
            fade(v)
            iv.setImageResource(R.drawable.wolfpic6)
        }
        else {
            fade(v)
            iv.setImageResource(R.drawable.wolfpic4)
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

        startActivityForResult(intent, REQUEST_FILE_PICKER)
    }

    lateinit var mCurrentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            FileUploadCandidate.file = this
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->

            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also { it ->
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    println("Fail!!!!!")
                    null // what should we do here, just pretend nothing happened?
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.bioni.seefoodprotov2",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)

                }
            }
        }
    }

    // this will get called when either the camera or file picker activity returns
    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {

        if (requestCode == REQUEST_FILE_PICKER && resultCode == Activity.RESULT_OK) {
            // when the user has selected an existing image from their gallery
            resultData?.data?.also { uri ->
                println(uri)

                FileUploadCandidate.file = File(getPath(applicationContext, uri))

                val intent = Intent(this, UploadActivity::class.java).apply {
                    // pass the image data to the upload activity
                    putExtra("source", "gallery")
                }
                startActivity(intent)
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            // when the user has taken a picture with their default camera app

            val intent = Intent(this, UploadActivity::class.java).apply {
                putExtra("source", "camera")
            }
            startActivity(intent)

        }
    }

    // this function code from https://www.coderpoint.info/questions/52694751/how-to-get-file-path-from-uri.html
    fun getPath(context:Context, uri:Uri): String? {

        val split = DocumentsContract.getDocumentId(uri).split(":")

        if ("com.android.externalstorage.documents" == uri.authority) {
            val type = split[0]
            if ("primary" == type.toLowerCase()) {
                return Environment.getExternalStorageDirectory().toString() + File.separator + split[1]
            }
        } else if ("com.android.providers.downloads.documents" == uri.authority) {
            val id = DocumentsContract.getDocumentId(uri)
            val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), id.toLong())
            return getDataColumn(context, contentUri, null, null)
        } else if ("com.android.providers.media.documents" == uri.authority) {
            val type = DocumentsContract.getDocumentId(uri).split(":")[0]

            var contentUri:Uri? = null

            if ("video" == type) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            } else if ("audio" == type) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            } else {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            }

            val selection = "_id=?"
            val selectionArgs = arrayOf(split[1])
            return getDataColumn(context, contentUri, selection, selectionArgs)
        }

        return null
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context           - The context.
     * @param uri               - The Uri to query.
     * @param selection         - (Optional) Filter used in the query.
     * @param selectionArgs     - (Optional) Selection arguments used in the query.
     * @return                  - The value of the _data column, which is typically a file path.
     */
    private fun getDataColumn(
        context: Context, uri: Uri,
        selection: String?, selectionArgs: Array<String>?
    ): String? {
        // this function code from https://www.coderpoint.info/questions/52694751/how-to-get-file-path-from-uri.html
        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(
                uri, projection,
                selection, selectionArgs, null
            )

            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }

        return null
    }


}
