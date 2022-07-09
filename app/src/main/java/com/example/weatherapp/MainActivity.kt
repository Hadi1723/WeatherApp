package com.example.weatherapp

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import com.squareup.picasso.Picasso
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {

    //declaring all elements in the "activity_main.xml"
    private lateinit var homeRl: RelativeLayout
    private lateinit var loadingPB: ProgressBar
    private lateinit var cityNameTV: TextView
    private lateinit var temperatureTV: TextView
    private lateinit var conditionTV: TextView
    private lateinit var weatherRv: RecyclerView
    private lateinit var cityEdt: EditText
    private lateinit var backTv: ImageView
    private lateinit var iconTV: ImageView
    private lateinit var feelsLikeTemperatureTV: TextView

    private lateinit var city: String


    private var changeInUnit: Int = 1


    private lateinit var searchTV: ImageView

    //incorporating each item in recyclerview
    private lateinit var weatherRVModelArrayList: ArrayList<WeatherRVModel>
    //incorporating the recyclerview item
    private lateinit var weatherRvAdapter: WeatherRvAdapter

    private var latitude: Double? = null
    private var longitude: Double? = null

    private lateinit var geocoder: Geocoder
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var humidity: Double? = null
    private var wind_kph: Double? = null
    private var wind_dir: String = ""
    private var wind_mph: Double? = null
    private var precepitation_mm: Double? = null
    private var precepitation_inch: Double? = null
    private var gust_mph: Double? = null
    private var gust_kph: Double? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //hiding menu bar at top
        //supportActionBar?.hide()

        //changeUnit = intent.getIntExtra("unit", 0)


        //defining the instance of the getLocation Provider
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        //used to get address
        geocoder = Geocoder(this, Locale.getDefault())


        homeRl = findViewById(R.id.idRLHome)
        loadingPB = findViewById(R.id.idPBLoading)
        cityNameTV = findViewById(R.id.idTVCityName)

        temperatureTV = findViewById(R.id.idInfo)

        conditionTV = findViewById(R.id.idTVCondition)
        weatherRv = findViewById(R.id.idRvWeather)
        cityEdt = findViewById(R.id.idTILCity)
        backTv = findViewById(R.id.idIVBack)
        iconTV = findViewById(R.id.idTVIcon)
        feelsLikeTemperatureTV = findViewById(R.id.idTVTemperatureFeelsLike)

        searchTV = findViewById(R.id.idTVSearch)

        //defining list that contain each item in recylerview item
        weatherRVModelArrayList = arrayListOf()

        //laying out the entire recyclerview
        weatherRvAdapter = WeatherRvAdapter(this, weatherRVModelArrayList)
        weatherRv.adapter = weatherRvAdapter

        //getting name of last location
        val task: Task<Location> = fusedLocationProviderClient.lastLocation


        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&  ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),101)

            return
        }

        task.addOnSuccessListener {
            if (it != null) {
                //Toast.makeText(applicationContext,"${it.longitude} ${it.longitude}", Toast.LENGTH_LONG).show()

                latitude = it.latitude
                longitude = it.longitude

                val address = geocoder.getFromLocation(latitude!!, longitude!!, 1)


                city = address.get(0).locality

                //address.get(0).getAddressLine(0)

                //locationName.text = "${address.get(0).getAddressLine(0)}"
                //locationName.text = "${address}"

                //getting city name
                // String city = addresses.get(0).getAddressLine(1);

                getWeatherInfo(city, changeInUnit)


            }
        }


        //incorporating the search button
        searchTV.setOnClickListener{
            if (cityEdt.text.isEmpty()) {
                //cityNameTV.text = "cityEdt.text"
                Toast.makeText(this, "Please enter the city name", Toast.LENGTH_SHORT).show()
            } else {
                city = cityEdt.text.toString()
                getWeatherInfo(cityEdt.text.toString(), changeInUnit)
            }
        }

    }

    /*
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1){
            if (grantResults.size > 0 && grantResults[0]==PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted",Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please provide permisssions", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    } */

    private fun getWeatherInfo(cityName: String, unit: Int) {
        var url: String = "http://api.weatherapi.com/v1/forecast.json?key=f48bc232b0454e428d3171151220507&q="+cityName+"&days=1&aqi=yes&alerts=yes"
        //cityNameTV.text = "City: ${cityName}"
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)

        // Request a string response
        // from the provided URL.
        val stringReq = StringRequest(Request.Method.GET, url,
            Response.Listener{ response ->
                Log.e("lat", response.toString())

                // get the JSON object
                val obj = JSONObject(response.toString())

                //temperatureTV.text = obj.toString()

                // get the Array from obj of name - "data"
                val arr = obj.getJSONObject("current")

                cityNameTV.text = "${arr.getJSONObject("condition").getString("text")} at ${cityName}"

                weatherRVModelArrayList.clear()

                if (unit == 0) {
                    // get the JSON object from the
                    // array at index position 0
                    val obj2 = arr.getString("temp_f")
                    //Log.e("lat obj2", obj2.toString())

                    Picasso.get().load("http:".plus(arr.getJSONObject("condition").getString("icon"))).into(iconTV)

                    // set the temperature and the city
                    // name using getString() function
                    temperatureTV.text =
                        "Real Temperature: ${obj2} °F"

                    feelsLikeTemperatureTV.text = "Feels Like: ${arr.getString("feelslike_f")} °F"

                    var calender: Calendar = Calendar.getInstance()
                    //var getDay: SimpleDateFormat = SimpleDateFormat("EEEE")
                    //var day: String = getDay.format(calender.time)
                    conditionTV.text = "${calender.time}"

                    var hourArray: JSONArray = obj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour")

                    for (i in 0..(hourArray.length()-1)) {
                        var hourObj: JSONObject = hourArray.getJSONObject(i)
                        var time = hourObj.getString("time")
                        var temper = hourObj.getString("temp_f")
                        var img = hourObj.getJSONObject("condition").getString("icon")
                        var wind = hourObj.getString("wind_kph")
                        weatherRVModelArrayList.add(WeatherRVModel(time, temper, img, wind, 0))
                    }
                    weatherRvAdapter.notifyDataSetChanged()

                } else {
                    // get the JSON object from the
                    // array at index position 0
                    val obj2 = arr.getString("temp_c")
                    //Log.e("lat obj2", obj2.toString())

                    Picasso.get().load("http:".plus(arr.getJSONObject("condition").getString("icon"))).into(iconTV)

                    // set the temperature and the city
                    // name using getString() function
                    temperatureTV.text =
                        "Real Temperature: ${obj2} °C"

                    feelsLikeTemperatureTV.text = "Feels Like: ${arr.getString("feelslike_c")} °C"

                    var calender: Calendar = Calendar.getInstance()
                    //var getDay: SimpleDateFormat = SimpleDateFormat("EEEE")
                    //var day: String = getDay.format(calender.time)
                    conditionTV.text = "Last updated on: ${calender.time}"

                    var hourArray: JSONArray = obj.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour")

                    for (i in 0..(hourArray.length()-1)) {
                        var hourObj: JSONObject = hourArray.getJSONObject(i)
                        var time = hourObj.getString("time")
                        var temper = hourObj.getString("temp_c")
                        var img = hourObj.getJSONObject("condition").getString("icon")
                        var wind = hourObj.getString("wind_kph")
                        weatherRVModelArrayList.add(WeatherRVModel(time, temper, img, wind, 1))
                    }
                    weatherRvAdapter.notifyDataSetChanged()
                }

                humidity = arr.getDouble("humidity")
                wind_kph = arr.getDouble("wind_kph")
                wind_dir = arr.getString("wind_dir")
                wind_mph = arr.getDouble("wind_mph")
                precepitation_mm = arr.getDouble("precip_mm")
                precepitation_inch = arr.getDouble("precip_in")
                gust_mph = arr.getDouble("gust_mph")
                gust_kph = arr.getDouble("gust_kph")

            },
            // In case of any error
           Response.ErrorListener { temperatureTV!!.text = "That didn't work!" })
        requestQueue.add(stringReq)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemSelected = item.itemId
            when(itemSelected) {
                R.id.celcius -> {
                    changeInUnit = 1
                    getWeatherInfo(city, changeInUnit)
                }
                R.id.fareheit -> {
                    changeInUnit = 0
                    getWeatherInfo(city, changeInUnit)
                }
                else -> {
                    val intent = Intent(this, DetailActivity::class.java).apply {
                        putExtra("humidity", humidity)
                        putExtra("wind_kph", wind_kph)
                        putExtra("wind_dir", wind_dir)
                        putExtra("wind_mph", wind_mph)
                        putExtra("precip_mm", precepitation_mm)
                        putExtra("precip_in", precepitation_inch)
                        putExtra("gust_mph", gust_mph)
                        putExtra("gust_kph", gust_kph)

                    }
                    startActivity(intent)
                }
            }

        return super.onOptionsItemSelected(item)
    }

}