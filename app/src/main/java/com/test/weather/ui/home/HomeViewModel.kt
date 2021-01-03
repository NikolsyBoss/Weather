package com.test.weather.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.test.weather.Repository.Weather
import com.test.weather.Repository.Location
import com.test.weather.Repository.weatherService.weatherResponse.WeatherResponse


class HomeViewModel constructor(application: Application) : AndroidViewModel(application) {


    var weatherResponseMutableLiveData: MutableLiveData<WeatherResponse> =
        MutableLiveData<WeatherResponse>()

    init {
        weatherResponseMutableLiveData.value = Weather(
            getApplication(), Location(
                getApplication()
            ).location
        ).getLocationWeather().value
    }

    val displayedWeatherResponse: MutableLiveData<WeatherResponse>
        get() = weatherResponseMutableLiveData

    fun updateWeather() {
        weatherResponseMutableLiveData.value = Weather(
            getApplication(), Location(
                getApplication()
            ).location
        ).getLocationWeather().value
    }

}