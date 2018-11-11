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


class MainActivity : AppCompatActivity() {

    private val REQUEST_FILE_PICKER: Int = 9843
    private val REQUEST_TAKE_PHOTO: Int = 4782

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ask for permissions upfront if they aren't granted
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            val writePermission = Array(1) { Manifest.permission.CAMERA}
            requestPermissions(writePermission, 16846846)
        }

        // set actions for the navigation buttons
        val openCameraButton = findViewById<Button>(R.id.open_camera_btn)
        openCameraButton.setOnClickListener {

            // opens the camera directly
            dispatchTakePictureIntent()

            // uncomment the following to use the CameraActivity, instead of going directly to the system file picker
//            val intent = Intent(this, CameraActivity::class.java).apply {
//                putExtra("Thing to do", "Start Cameraing!") // what does this do? nobody knows
//            }
//            startActivity(intent)
        }
        val openGalleryActivity= findViewById<Button>(R.id.open_gallery_btn)
        openGalleryActivity.setOnClickListener {

            // opens the system file picker
            performFileSearch()

            // uncomment the following to use the GalleryActivity, instead of going directly to the system file picker
//            val intent = Intent(this, GalleryActivity::class.java).apply {
//                putExtra("Thing to do", "Start Gallerying!") // what does this do? nobody knows
//            }
//            startActivity(intent)
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

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // REQUEST_FILE_PICKER. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == REQUEST_FILE_PICKER && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            resultData?.data?.also { uri ->
                println(uri)

                // TODO: this seems to be working right, but we need to figure out how to pass the image to the upload activity properly

                val intent = Intent(this, UploadActivity::class.java).apply {
                    // pass the image data to the upload activity
                    FileUploadCandidate.file = File(uri.path)
                    putExtra("path", uri.path) // what does this do? nobody knows
                    putExtra("path_encoded", uri.encodedPath)
                    putExtra("uri_string", uri.toString())
                }
                startActivity(intent)
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also {uri ->
                print(uri)

                // TODO: for some reason resultCode is coming back -1 (failing)

                val intent = Intent(this, UploadActivity::class.java).apply {
                    // pass the image data to the upload activity
                    FileUploadCandidate.file = File(uri.path)
                    putExtra("path", uri.path) // what does this do? nobody knows
                    putExtra("uri_string", uri.toString())
                }
                startActivity(intent)

            }
        }
    }
}
