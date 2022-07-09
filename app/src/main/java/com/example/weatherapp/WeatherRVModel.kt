package com.example.weatherapp

import kotlin.properties.Delegates

class WeatherRVModel {

    private lateinit var time: String
    private lateinit var temperature: String
    private lateinit var icon: String
    private lateinit var windSpeed: String
    private var unit by Delegates.notNull<Int>()

    constructor(time: String, temperature: String, icon: String, windSpeed: String, unit: Int) {
        this.time = time
        this.temperature = temperature
        this.icon = icon
        this.windSpeed = windSpeed
        this.unit = unit
    }

    fun getTime(): String {
        return time
    }

    fun setTime(time: String) {
        this.time = time
    }

    fun getTemperature(): String {
        return temperature
    }

    fun setTemperature(temperature: String) {
        this.temperature = temperature
    }

    fun getIcon(): String {
        return icon
    }

    fun setIcon(icon: String) {
        this.icon = icon
    }

    fun getWindSpeed(): String {
        return windSpeed
    }

    fun setWindSpeed(windSpeed: String) {
        this.windSpeed = windSpeed
    }

    @JvmName("getUnit1")
    fun getUnit(): Int {
        return unit
    }

    @JvmName("setUnit1")
    fun setUnit(unit: Int) {
        this.unit = unit
    }

}