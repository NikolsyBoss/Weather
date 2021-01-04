package com.test.weather.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.weather.R
import com.test.weather.Repository.WeatherWorker
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val permission: Array<String> = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.INTERNET)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            initView()
        }
        getPermission()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        initView()
    }

    private fun initView(){

        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.navigation_home, R.id.navigation_search, R.id.navigation_settings
                )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        createNotification()
    }

    private fun getPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permission, 3)
            }
        }
    }

    private fun createNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val task = PeriodicWorkRequest.Builder(
                WeatherWorker::class.java,
                10, TimeUnit.MINUTES
            ).build()
            val workManager = WorkManager.getInstance()
            workManager.enqueue(task)
        }
    }
}
