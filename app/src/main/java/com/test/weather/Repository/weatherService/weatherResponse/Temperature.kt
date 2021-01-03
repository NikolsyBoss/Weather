package com.test.weather.Repository.weatherService.weatherResponse

import com.google.gson.annotations.SerializedName

data class Temperature (

	@SerializedName("temp") var temp : String,
	@SerializedName("feels_like") val feels_like : Double,
	@SerializedName("temp_min") var temp_min : String,
	@SerializedName("temp_max") var temp_max : String,
	@SerializedName("pressure") val pressure : Int,
	@SerializedName("humidity") val humidity : Int
)