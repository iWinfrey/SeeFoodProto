# CEG 4110 Group Project:
# SeeFood 10.8 Mountain Lion Xtreme Edition

This is a simple app which contains two functions:
1. Allow a user to select an image from their gallery or take an image with their camera
2. Upload that image to a remote server
3. Rate the image as being food or not food
4. Allow the user to view previously uploaded images and their ratings

# Requirements

This app needs only four permissions: read, write, camera and internet. Read is used to allow the user to select an image from their device, write is used to save an image to their device, camera is used to take a new image with the user's camera, and internet is required to communicate with the remote server. 

Development was done against API Level 28 / Android 9.0. It may, but it is not guaranteed to work with any lower version.

Running the APK should be as easy as loading it onto your device and installing. Apps from unknown sources must be enabled. This app is not available on the app store, it would simply generate too many sales.

# Development and Design

This project was written in Kotlin, as it is now Android's first class language. All developers were already familiar with Java. Android Studio was the IDE used. Testing was done on a Huawei Nexus 6P, Samsung Galaxy Note 8, and various emulated devices. 

The app is implemented with several classes:

### MainActivity

This is the starting activity, and the home screen for the app. From here, the user can open new activities to take a picture, select a picture from their gallery, or view previously uploaded images.

<img src="/img/Screenshot_1543874872.png" height="300">

### UploadActivity

There is no activity for camera or gallery, since those function are handled by the user's default apps. Once they take or select an image, they are taken here. This screen consists of only an upload button or a button to go back to the previous action (whether that be camera or gallery).

<img src="/img/Screenshot_1543875342.png" height="300">

### ResultsActivity

The results activity opens after the user's image has been classified by the server and the results are received by the app.

<img src="/img/Screenshot_1543875346.png" height="300">

### HistoryActivity

The history activity shows previously classified images and their ratings. When the activity is opened, a call is made to the server to return image thumbnails and classification data. This data is then displayed for the user. 

<img src="/img/Screenshot_1543875118.png" height="300">

### StatsActivity

On this activity the user can see statistics related to images uploaded over time, in relation to how many were food or not food, and how many total.

<img src="/img/Screenshot_1543876874.png" height="300">

### UploadImageService and GetImageService

In Android, network operations are not allowed to run on the main UI thread. For network operations, it is necessary to create a service which can be run asynchronously. For uploading images to the server or pulling down images from the server, we have two separate services.  


# References

The following resources were used for this project:

+ [The Official Android Documentation](https://developer.android.com/docs/)
