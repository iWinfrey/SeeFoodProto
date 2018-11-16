package com.example.bioni.seefoodprotov2

import java.io.File

// this is a singleton object which just stores the image file so that the upload activity can get it
object CandidateImage {
    var file: File? = null
    var classification: String? = null
}