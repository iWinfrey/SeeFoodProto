package com.example.bioni.seefoodprotov2

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.gridview_item.view.*


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
        foodView.imageItem.setImageURI(Uri.parse(image.imagePath))
        foodView.imageScore.text = image.title

        return foodView
    }
}