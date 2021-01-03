package com.test.weather.Repository

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationListener


class Location(private val application: Application): LocationListener {

    private lateinit var locationManager: LocationManager
    var location: Location? = null

    init {
        callLocation()
    }

    private fun callLocation() {
        locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val provider = locationManager.getBestProvider(Criteria(), false)
        val location = if (ActivityCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                application,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            return
        } else
            locationManager.getLastKnownLocation(provider)
        onLocationChanged(location)
    }



    override fun onLocationChanged(location: Location?) {
        this.location = location
    }
}
