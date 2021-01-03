package com.test.weather.Repository.weatherService.weatherResponse

import com.google.gson.annotations.SerializedName

data class Clouds (

	@SerializedName("all") val all : Int
)