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
import android.media.Image
import android.os.Handler
import android.provider.DocumentsContract
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity() {

    private val REQUEST_FILE_PICKER: Int = 9843
    private val REQUEST_TAKE_PHOTO: Int = 4782

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check for read and write external storage permissions, if we haven't already been granted them
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val readPermission = Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE}
            requestPermissions(readPermission, 9995)
        }
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            val writePermission = Array(1) { Manifest.permission.WRITE_EXTERNAL_STORAGE}
            requestPermissions(writePermission, 9991)
        }
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            val internetPermission = Array(1) { Manifest.permission.INTERNET}
            requestPermissions(internetPermission, 9998)
        }

        // set actions for the navigation buttons
        val openCameraButton = findViewById<Button>(R.id.open_camera_btn)
        openCameraButton.setOnClickListener {
            takeFromCamera()
        }

        val openGalleryActivity= findViewById<Button>(R.id.open_gallery_btn)
        openGalleryActivity.setOnClickListener {
            pickFromGallery()
        }

        val openHistoryButton = findViewById<Button>(R.id.open_history_btn)
        openHistoryButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra("Thing to do", "Start Historying!") // what does this do? nobody knows
            }
            startActivity(intent)
        }

        // see if there is a value for "do"
        val thingToDo: String?
        thingToDo = if (savedInstanceState == null) {
            val extras = intent.extras
            if (extras == null) {
                null
            } else {
                extras.getString("do_thing")
            }
        } else {
            savedInstanceState?.getSerializable("do_thing") as String
        }
        // were we sent back here to take another pic / select another pic?
        if (thingToDo == "gallery") {
            pickFromGallery()
        } else if (thingToDo == "camera") {
            takeFromCamera()
        }

        val img1 = findViewById<ImageView>(R.id.iv1)
        val img2 = findViewById<ImageView>(R.id.iv2)
        val img3 = findViewById<ImageView>(R.id.iv3)
        val img4 = findViewById<ImageView>(R.id.iv4)
        val img5 = findViewById<ImageView>(R.id.iv5)
        val img6 = findViewById<ImageView>(R.id.iv6)
        val img7 = findViewById<ImageView>(R.id.iv7)
        val img8 = findViewById<ImageView>(R.id.iv8)
        val img9 = findViewById<ImageView>(R.id.iv9)
        val img10 = findViewById<ImageView>(R.id.iv10)

        var food = arrayOf(img1, img2, img3, img4, img5, img6, img7, img8, img9, img10)

        var handler1 = Handler()
        var runnable1: Runnable = object : Runnable {
            override fun run() {
                rotatePictures(food)
                handler1.postDelayed(this, 500)
            }
        }
        handler1.postDelayed(runnable1, 1000)
    }

    fun rotatePictures(array: Array<ImageView>){
        for(img in array){
            if(img.tag == "1"){
                img.setImageResource(R.drawable.mainmilk)
                img.setTag("10")
            }else if(img.tag == "2"){
                img.setImageResource(R.drawable.mainapple)
                img.setTag("1")
            }else if(img.tag == "3"){
                img.setImageResource(R.drawable.mainburger)
                img.setTag("2")
            }else if(img.tag == "4"){
                img.setImageResource(R.drawable.maincake)
                img.setTag("3")
            }else if(img.tag == "5"){
                img.setImageResource(R.drawable.mainhotdog)
                img.setTag("4")
            }else if(img.tag == "6"){
                img.setImageResource(R.drawable.maintaco)
                img.setTag("5")
            }else if(img.tag == "7"){
                img.setImageResource(R.drawable.maincarrot)
                img.setTag("6")
            }else if(img.tag == "8"){
                img.setImageResource(R.drawable.maincheese)
                img.setTag("7")
            }else if(img.tag == "9"){
                img.setImageResource(R.drawable.maindonut)
                img.setTag("8")
            }else if(img.tag == "10"){
                img.setImageResource(R.drawable.maincoffee)
                img.setTag("9")
            }
        }
    }

    fun pickFromGallery() {
        clearOldImage() // clear any image that is in the singleton object already
        performFileSearch() // opens the system file picker
    }

    fun takeFromCamera() {
        clearOldImage() // clear any image that is in the singleton object already

        // check for camera permissions, if we haven't already been granted them
        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            val cameraPermission = Array(1) { Manifest.permission.CAMERA}
            requestPermissions(cameraPermission, 99)
        }

        // opens the camera directly
        dispatchTakePictureIntent()
    }

    fun clearOldImage() {
        CandidateImage.file = ArrayList()
        CandidateImage.classification = ArrayList()
        CandidateImage.source = null
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
            action = Intent.ACTION_GET_CONTENT
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        }
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), REQUEST_FILE_PICKER)
        //startActivityForResult(intent, REQUEST_FILE_PICKER)
    }

    lateinit var mCurrentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            CandidateImage.file?.add(this)
            CandidateImage.source = "camera"
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

        if (requestCode == REQUEST_FILE_PICKER) {
            // when the user has selected an existing image from their gallery
            if (resultCode == Activity.RESULT_OK) {
                Log.d("Test1", "It got here!")
                if(resultData?.clipData != null) {
                    Log.d("Test2", "It got here also!")
                    val count = resultData.clipData.itemCount; //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for(i in 0 until count) {
                        val uri = resultData.clipData.getItemAt(i).uri
                        Log.d("Test", uri.toString())

                        CandidateImage.file?.add(File(getPath(applicationContext, uri)))
                        CandidateImage.source = "gallery"
                        //do something with the image (save it to some directory or whatever you need to do with it here)
                    }
                    val intent = Intent(this, UploadActivity::class.java).apply {
                        // pass the image data to the upload activity
                        putExtra("source", "gallery")
                    }
                    startActivity(intent)
                } else {
                    resultData?.data?.also { uri ->
                        println(uri)

                        CandidateImage.file?.add(File(getPath(applicationContext, uri)))
                        CandidateImage.source = "gallery"
                        val intent = Intent(this, UploadActivity::class.java).apply {
                            // pass the image data to the upload activity
                            putExtra("source", "gallery")
                        }
                        startActivity(intent)
                    }
                }
            } else {
                resultData?.data?.also { uri ->
                    println(uri)

                    CandidateImage.file?.add(File(getPath(applicationContext, uri)))
                    CandidateImage.source = "gallery"
                    val intent = Intent(this, UploadActivity::class.java).apply {
                        // pass the image data to the upload activity
                        putExtra("source", "gallery")
                    }
                    startActivity(intent)
                }
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
