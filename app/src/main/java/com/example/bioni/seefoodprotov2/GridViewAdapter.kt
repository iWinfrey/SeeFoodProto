package com.example.bioni.seefoodprotov2

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.gridview_item.view.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar
import com.example.bioni.seefoodprotov2.R.id.imageItem


class GridViewAdapter(context: Context, var list: ArrayList<GridImage>, val type: String) : BaseAdapter() {
    var context: Context? = context

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val image = this.list[position]

        val inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var foodView = inflater.inflate(R.layout.gridview_item, null)
        if (this.type == "History" ) {
            DownloadImageTask(foodView.imageItem).execute(image.imagePath)
            foodView.imageScore.text = image.title
            val progress1 = foodView.progress_1 as RoundCornerProgressBar
            progress1.setProgressBackgroundColor(Color.parseColor("#ffffff"))
            progress1.setMax(60F)

            if(image.title == "High No"){
                progress1.setProgressColor(Color.parseColor("#b20000"))
                progress1.setProgress(10F)
            } else if(image.title == "Moderate No"){
                progress1.setProgressColor(Color.parseColor("#ff4500"))
                progress1.setProgress(20F)
            } else if(image.title == "Low No"){
                progress1.setProgressColor(Color.parseColor("#ffff33"))
                progress1.setProgress(30F)
            } else if(image.title == "Low Yes"){
                progress1.setProgressColor(Color.parseColor("#228B22"))
                progress1.setProgress(40F)
            } else if(image.title == "Moderate Yes"){
                progress1.setProgressColor(Color.parseColor("#3232cd"))
                progress1.setProgress(50F)
            } else if(image.title == "High Yes"){
                progress1.setProgressColor(Color.parseColor("#4b0082"))
                progress1.setProgress(60F)
            }
        } else if (this.type == "Results") {
            foodView.imageItem.setImageURI(Uri.parse(image.imagePath))
            foodView.imageScore.text = image.title
            val progress1 = foodView.progress_1 as RoundCornerProgressBar
            progress1.setProgressBackgroundColor(Color.parseColor("#ffffff"))
            progress1.setMax(60F)

            if(image.title == "High No"){
                progress1.setProgressColor(Color.parseColor("#b20000"))
                progress1.setProgress(10F)
            } else if(image.title == "Moderate No"){
                progress1.setProgressColor(Color.parseColor("#ff4500"))
                progress1.setProgress(20F)
            } else if(image.title == "Low No"){
                progress1.setProgressColor(Color.parseColor("#ffff33"))
                progress1.setProgress(30F)
            } else if(image.title == "Low Yes"){
                progress1.setProgressColor(Color.parseColor("#228B22"))
                progress1.setProgress(40F)
            } else if(image.title == "Moderate Yes"){
                progress1.setProgressColor(Color.parseColor("#3232cd"))
                progress1.setProgress(50F)
            } else if(image.title == "High Yes"){
                progress1.setProgressColor(Color.parseColor("#4b0082"))
                progress1.setProgress(60F)
            }
        }
        else {
            foodView = inflater.inflate(R.layout.gridview_item_upload, null)
            foodView.imageItem.setImageURI(Uri.parse(image.imagePath))
        }

        return foodView
    }
}

private class DownloadImageTask(bmImage: ImageView) : AsyncTask<String, Void, Bitmap>() {
    private var bmImage = bmImage
    override fun doInBackground(vararg urls: String): Bitmap? {
        val urlDisplay = urls[0]
        var mIcon11: Bitmap? = null
        try {
            val `in` = java.net.URL(urlDisplay).openStream()
            mIcon11 = BitmapFactory.decodeStream(`in`)
        } catch (e: Exception) {
            Log.e("Error", e.message)
            e.printStackTrace()
        }

        return mIcon11
    }

    override fun onPostExecute(result: Bitmap) {
        bmImage.setImageBitmap(result)
    }
}