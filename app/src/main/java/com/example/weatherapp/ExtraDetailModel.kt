package com.example.weatherapp

class ExtraDetailModel {
    private lateinit var extraName: String
    private lateinit var extraDetail: String

    constructor(extraName: String, extraDetail:String) {
        this.extraName = extraName
        this.extraDetail = extraDetail

    }

    fun getExtraName(): String {
        return extraName
    }

    fun setExtraName(extraName: String) {
        this.extraName = extraName
    }

    fun getExtraDetail(): String {
        return extraDetail
    }

    fun setExtraDetail(extraDetail: String) {
        this.extraDetail = extraDetail
    }

}