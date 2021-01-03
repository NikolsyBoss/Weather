package com.test.weather.Repository.weatherService.weatherResponse

import com.google.gson.annotations.SerializedName

data class Location (

	@SerializedName("lon") val lon : Double,
	@SerializedName("lat") val lat : Double
)