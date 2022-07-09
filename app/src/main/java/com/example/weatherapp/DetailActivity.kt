package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class DetailActivity : AppCompatActivity() {

    private lateinit var searchItem: TextView
    private lateinit var detailRv: RecyclerView


    //incorporating each item in recyclerview
    private lateinit var detailArrayList: ArrayList<ExtraDetailModel>
    //incorporating the recyclerview item
    private lateinit var detailAdapter: ExtraDetailAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        searchItem = findViewById(R.id.editTextTextPersonName)
        detailRv = findViewById(R.id.extra)

        //defining list that contain each item in recylerview item
        detailArrayList = arrayListOf()

        //laying out the entire recyclerview
        detailAdapter = ExtraDetailAdapter(this, detailArrayList)
        detailRv.adapter = detailAdapter

        detailArrayList.add(ExtraDetailModel("Humidity (%)", intent.getDoubleExtra("humidity", 0.0).toString()))
        detailArrayList.add(ExtraDetailModel("Wind Speed (Km/h)", intent.getDoubleExtra("wind_kph", 0.0).toString()))
        detailArrayList.add(ExtraDetailModel("Wind Speed (Mph)", intent.getDoubleExtra("wind_mph", 0.0).toString()))
        detailArrayList.add(ExtraDetailModel("Wind Direction", intent.getStringExtra("wind_dir").toString()))
        detailArrayList.add(ExtraDetailModel("Precipitation Amount (mm)", intent.getDoubleExtra("precip_mm", 0.0).toString()))
        detailArrayList.add(ExtraDetailModel("Precipitation Amount (inches)", intent.getDoubleExtra("precip_in", 0.0).toString()))
        detailArrayList.add(ExtraDetailModel("Gust Speed (Km/h)", intent.getDoubleExtra("gust_kph", 0.0).toString()))
        detailArrayList.add(ExtraDetailModel("Gust Speed (Mph)", intent.getDoubleExtra("gust_mph", 0.0).toString()))


                /*
                                putExtra("precip_mm", precepitation_mm)
                                putExtra("precip_in", precepitation_inch)
                                putExtra("gust_mph", gust_mph)
                                putExtra("gust_kph", gust_kph)
                 */

        detailAdapter.notifyDataSetChanged()


    }
}