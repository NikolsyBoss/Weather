package com.test.weather.ui.home

import android.app.Application
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.test.weather.R
import com.test.weather.Repository.Location
import com.test.weather.Repository.Weather
import com.test.weather.Repository.WeatherWorker
import com.test.weather.Repository.weatherService.weatherResponse.WeatherResponse
import java.util.concurrent.TimeUnit


class HomeViewModel constructor(application: Application) : AndroidViewModel(application) {

    private var preferenceManager = PreferenceManager.getDefaultSharedPreferences(application)
    private val isEnableCountry:Boolean = preferenceManager.getBoolean(application.getString(R.string.key_country).toString(), false)

    var weatherResponseMutableLiveData: MutableLiveData<WeatherResponse> =
                                                                MutableLiveData<WeatherResponse>()

    init {
        weatherResponseMutableLiveData.value = Weather(
            application.applicationContext, Location(application.applicationContext).location
        ).getLocationWeather().value
    }

    public val displayedWeatherResponse: MutableLiveData<WeatherResponse>
        get() = weatherResponseMutableLiveData

    public fun updateWeather() {
        weatherResponseMutableLiveData.value = Weather(
            getApplication(), Location(
                getApplication()
            ).location
        ).getLocationWeather().value
    }
}