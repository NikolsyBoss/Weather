package com.test.weather.Repository

import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.preference.PreferenceManager
import com.test.weather.R
import com.test.weather.Repository.weatherService.WeatherService
import com.test.weather.Repository.weatherService.weatherResponse.WeatherResponse
import retrofit2.Response

class Weather constructor(_context: Context, private val location: Location?)  {

    private var units:String

    private var context = _context

    private var preferenceManager = PreferenceManager.getDefaultSharedPreferences(context)

    init{
        units = preferenceManager.getString(
            context.getString(R.string.key_units),
            context.getString(R.string.default_settings_units)
        )!!
    }

    private fun weatherLocationRequest() : MutableLiveData<WeatherResponse> {
        val data = MutableLiveData<WeatherResponse>()
        var response:Response<WeatherResponse>? = null
        val thread = Thread(Runnable {
            try {
                response = WeatherService().getWeatherLocation(
                    location?.latitude ?: 0.0, location?.longitude ?: 0.0,
                    units, context.getString(R.string.api_key_openweathermap_org)
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
                    city, units, context.getString(R.string.api_key_openweathermap_org)
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

    private fun weatherLocationRequestNotification() : MutableLiveData<WeatherResponse> {
        val data = MutableLiveData<WeatherResponse>()
        var response:Response<WeatherResponse>? = null
        val thread = Thread(Runnable {
            try {
                response = WeatherService().getWeatherLocation(
                    location?.latitude ?: 0.0, location?.longitude ?: 0.0,
                    units, context.getString(R.string.api_key_openweathermap_org)
                ).execute()
                data.postValue(response!!.body())
            } catch (e: Exception) {

            }
        })
        thread.start()
        thread.join()
        //Log.d("weatherRequest", "ok " + response)
        return data
    }

    public fun getSearchWeather(city: String): MutableLiveData<WeatherResponse>{
        var weatherResponse = weatherSearchRequest(city)
        weatherResponse = addUnitsSymbol(weatherResponse,units)
        return weatherResponse
    }

    public fun getLocationWeather(): MutableLiveData<WeatherResponse>{
        var weatherResponse = weatherLocationRequest()
        weatherResponse = addUnitsSymbol(weatherResponse,units)
        return weatherResponse
    }


    public fun getLocationWeatherNotification(): MutableLiveData<WeatherResponse>{
        var weatherResponse = weatherLocationRequestNotification()
        weatherResponse = addUnitsSymbol(weatherResponse,units)
        return weatherResponse
    }


    private fun addUnitsSymbol(weatherResponse: MutableLiveData<WeatherResponse>, units:String) : MutableLiveData<WeatherResponse>{
        var weatherResponseWithSymbol = weatherResponse
        when (units) {
            context.getString(R.string.standard) -> {
                weatherResponseWithSymbol = addSymbol(weatherResponse, context.getString(R.string.Kelvin))
            }
            context.getString(R.string.metric) -> {
                weatherResponseWithSymbol = addSymbol(weatherResponse, context.getString(R.string.Celsius))
            }
            context.getString(R.string.imperial) -> {
                weatherResponseWithSymbol = addSymbol(weatherResponse, context.getString(R.string.Fahrenheit))
            }
        }
        return weatherResponseWithSymbol
    }

    private fun addSymbol(weatherResponse: MutableLiveData<WeatherResponse>,symbol:String): MutableLiveData<WeatherResponse>{
        if(weatherResponse.value != null) {
            weatherResponse.value!!.temperature.temp += symbol
            weatherResponse.value!!.temperature.temp_max += symbol
            weatherResponse.value!!.temperature.temp_min += symbol
        }
        return weatherResponse
    }
}