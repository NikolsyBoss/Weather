package com.test.weather.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.weather.Repository.Location
import com.test.weather.Repository.Weather
import com.test.weather.Repository.weatherService.weatherResponse.WeatherResponse

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var lastCity: String
    var weatherResponse: MutableLiveData<WeatherResponse> =
        MutableLiveData<WeatherResponse>()

    val displayedWeatherResponse: MutableLiveData<WeatherResponse>
        get() = weatherResponse

    var isNullWeatherResponse: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init{
        isNullWeatherResponse.value = weatherResponse.value != null
    }

    fun updateWeather() {
        weatherResponse.value = Weather(
            getApplication(), Location(
                getApplication()
            ).location
        ).getSearchWeather(lastCity).value
        isNullWeatherResponse()
    }

    fun searchWeather(city:String) {
        weatherResponse.value = Weather(
            getApplication(), Location(
                getApplication()
            ).location
        ).getSearchWeather(city).value
        lastCity = city
        isNullWeatherResponse()
    }

    fun isNullWeatherResponse(){
        isNullWeatherResponse.value = weatherResponse.value != null
    }
}