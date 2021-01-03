package com.test.weather.Repository.weatherService

import com.test.weather.Repository.weatherService.weatherResponse.WeatherResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface WeatherService {

    @GET("./data/2.5/weather")
    fun getWeatherCity(
        @Query("q") cityName: String,
        @Query("units") units: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponse>

    @GET("./data/2.5/weather")
    fun getWeatherLocation(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("units") units: String,
            @Query("appid") apiKey: String
    ): Call<WeatherResponse>


    companion object {
        operator fun invoke(): WeatherService {
            return Retrofit.Builder()
                    .baseUrl("http://api.openweathermap.org")
                    .client(okhttp3.OkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherService::class.java)
        }
    }
}