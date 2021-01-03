package com.test.weather.Repository.weatherService.weatherResponse

import com.google.gson.annotations.SerializedName

data class WeatherResponse (

    @SerializedName("coord") val location : Location,
    @SerializedName("weather") val weather : List<Weather>,
    @SerializedName("base") val base : String,
    @SerializedName("main") val temperature : Temperature,
    @SerializedName("visibility") val visibility : Int,
    @SerializedName("wind") val wind : Wind,
    @SerializedName("clouds") val clouds : Clouds,
    @SerializedName("dt") val dt : Int,
    @SerializedName("sys") val sys : Sys,
    @SerializedName("timezone") val timezone : Int,
    @SerializedName("id") val id : Int,
    @SerializedName("name") val city : String,
    @SerializedName("cod") val cod : Int
)