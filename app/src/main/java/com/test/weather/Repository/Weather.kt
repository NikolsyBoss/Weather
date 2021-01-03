package com.test.weather.Repository

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.test.weather.R
import com.test.weather.Repository.weatherService.WeatherService
import com.test.weather.Repository.weatherService.weatherResponse.WeatherResponse
import retrofit2.Response

class Weather constructor(private val application: Application, private val location: Location?)  {

    private var units:String

    private var preferenceManager = PreferenceManager.getDefaultSharedPreferences(application)

    init{
        units = preferenceManager.getString(
            application.getString(R.string.key_units),
            application.getString(R.string.default_settings_units)
        )!!
    }

    private fun weatherLocationRequest() : MutableLiveData<WeatherResponse> {
        val data = MutableLiveData<WeatherResponse>()
        var response:Response<WeatherResponse>? = null
        val thread = Thread(Runnable {
            try {
                response = WeatherService().getWeatherLocation(
                    location?.latitude ?: 0.0, location?.longitude ?: 0.0,
                    units, application.getString(R.string.api_key_openweathermap_org)
                ).execute()
            } catch (e: Exception) {

            }
        })
        thread.start()
        thread.join()
        Log.d("weatherRequest", "ok " + response)

        data.value = response?.body()
        return data
    }

    private fun weatherSearchRequest(city:String) : MutableLiveData<WeatherResponse> {
        val data = MutableLiveData<WeatherResponse>()
        var response:Response<WeatherResponse>? = null
        val thread = Thread(Runnable {
            try {
                response = WeatherService().getWeatherCity(
                    city, units, application.getString(R.string.api_key_openweathermap_org)
                ).execute()
            } catch (e: Exception) {

            }
        })
        thread.start()
        thread.join()
        Log.d("weatherRequest", "ok " + response)
        data.value = response?.body()
        return data
    }

    fun getSearchWeather(city: String): MutableLiveData<WeatherResponse>{
        var weatherResponse = weatherSearchRequest(city)
        weatherResponse = addUnitsSymbol(weatherResponse,units)
        return weatherResponse
    }

    fun getLocationWeather(): MutableLiveData<WeatherResponse>{
        var weatherResponse = weatherLocationRequest()
        weatherResponse = addUnitsSymbol(weatherResponse,units)
        return weatherResponse
    }



    private fun addUnitsSymbol(weatherResponse: MutableLiveData<WeatherResponse>, units:String) : MutableLiveData<WeatherResponse>{
        var weatherResponseWithSymbol = weatherResponse
        when (units) {
            application.getString(R.string.standard) -> {
                weatherResponseWithSymbol = addSymbol(weatherResponse, application.getString(R.string.Kelvin))
            }
            application.getString(R.string.metric) -> {
                weatherResponseWithSymbol = addSymbol(weatherResponse, application.getString(R.string.Celsius))
            }
            application.getString(R.string.imperial) -> {
                weatherResponseWithSymbol = addSymbol(weatherResponse, application.getString(R.string.Fahrenheit))
            }
        }
        return weatherResponseWithSymbol
    }

    fun addSymbol(weatherResponse: MutableLiveData<WeatherResponse>,symbol:String): MutableLiveData<WeatherResponse>{
        if(weatherResponse.value != null) {
            weatherResponse.value!!.temperature.temp += symbol
            weatherResponse.value!!.temperature.temp_max += symbol
            weatherResponse.value!!.temperature.temp_min += symbol
        }
        return weatherResponse
    }
}