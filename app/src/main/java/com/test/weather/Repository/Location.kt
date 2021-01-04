package com.test.weather.Repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat


class Location(_context: Context): LocationListener {

    private val context = _context
    private lateinit var locationManager: LocationManager
    public var location: Location? = null

    init {
        callLocation()
    }

    private fun callLocation() {
        locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location = if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            return
        } else
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        onLocationChanged(location)
    }



    override fun onLocationChanged(location: Location?) {
        this.location = location
    }


    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }


    override fun onProviderEnabled(provider: String?) {

    }


    override fun onProviderDisabled(provider: String?) {

    }
}
