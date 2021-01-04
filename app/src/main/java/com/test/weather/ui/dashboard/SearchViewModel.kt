package com.test.weather.ui.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.test.weather.R
import com.test.weather.Repository.Location
import com.test.weather.Repository.Weather
import com.test.weather.Repository.weatherService.weatherResponse.WeatherResponse

class SearchViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var lastCity: String
    public var weatherResponse: MutableLiveData<WeatherResponse> =
        MutableLiveData<WeatherResponse>()

    private var preferenceManager = PreferenceManager.getDefaultSharedPreferences(application)
    public val isEnableCountry:Boolean
            = preferenceManager.getBoolean(application.getString(R.string.key_country), true)

    public val displayedWeatherResponse: MutableLiveData<WeatherResponse>
        get() = weatherResponse

    public var isNullWeatherResponse: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init{
        isNullWeatherResponse.value = weatherResponse.value != null
    }

    public fun updateWeather() {
        weatherResponse.value = Weather(
            getApplication(), Location(
                getApplication()
            ).location
        ).getSearchWeather(lastCity).value
        isNullWeatherResponse()
    }

    public fun searchWeather(city:String) {
        weatherResponse.value = Weather(
            getApplication(), Location(
                getApplication()
            ).location
        ).getSearchWeather(city).value
        lastCity = city
        isNullWeatherResponse()
    }

    public fun isNullWeatherResponse(){
        isNullWeatherResponse.value = weatherResponse.value != null
    }
}