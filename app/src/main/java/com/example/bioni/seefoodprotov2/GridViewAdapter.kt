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
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import com.example.bioni.seefoodprotov2.R.id.imageItem


class GridViewAdapter(context: Context, var list: ArrayList<GridImage>) : BaseAdapter() {
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
        val foodView = inflater.inflate(R.layout.gridview_item, null)
        DownloadImageTask(foodView.imageItem).execute(image.imagePath)
        foodView.imageScore.text = image.title

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