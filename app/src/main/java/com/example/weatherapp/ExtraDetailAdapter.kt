package com.example.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ExtraDetailAdapter(
    private val context: Context,
    private val detailModels: ArrayList<ExtraDetailModel>

) : RecyclerView.Adapter<ExtraDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExtraDetailAdapter.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.detail_item, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return detailModels.size
    }

    // and we're going to define our own view holder which will encapsulate the memory card view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val nameTv = itemView.findViewById<TextView>(R.id.itemTitle)
            val detailTv = itemView.findViewById<TextView>(R.id.idInfo)

            var model: ExtraDetailModel = detailModels.get(position)

            nameTv.setText(model.getExtraName())
            detailTv.setText(model.getExtraDetail())

            //var input: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
            //var output: SimpleDateFormat = SimpleDateFormat("hh:mm aa")

            /*
            try {
                var t: Date = input.parse(model.getTime())
                timeTV.setText(output.format(t))
            } catch (e: ParseException) {
                e.printStackTrace()
            } */
        }

    }


}