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

class WeatherRvAdapter(
    private val context: Context,
    private val weatherModels: ArrayList<WeatherRVModel>

) : RecyclerView.Adapter<WeatherRvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherRvAdapter.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.weather_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherRvAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return weatherModels.size
    }

    // and we're going to define our own view holder which will encapsulate the memory card view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(position: Int) {
            val windTv = itemView.findViewById<TextView>(R.id.idTVWindSpeed)
            val temperaturetv = itemView.findViewById<TextView>(R.id.idInfo)
            val timeTV = itemView.findViewById<TextView>(R.id.itemTitle)
            val conditionTV = itemView.findViewById<ImageView>(R.id.idTVCondition)

            var model: WeatherRVModel = weatherModels.get(position)

            if (model.getUnit() == 1) {
                temperaturetv.setText(model.getTemperature() + "°C")
            } else {
                temperaturetv.setText(model.getTemperature() + "°F")
            }
            Picasso.get().load("http:".plus(model.getIcon())).into(conditionTV)
            windTv.setText(model.getWindSpeed() + "Km/h")
            var input: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm")
            var output: SimpleDateFormat = SimpleDateFormat("hh:mm aa")

            try {
                var t: Date = input.parse(model.getTime())
                timeTV.setText(output.format(t))
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }



    }


}