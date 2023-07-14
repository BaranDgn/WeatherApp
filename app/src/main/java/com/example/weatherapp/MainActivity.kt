package com.example.weatherapp

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.weatherapp.domain.util.DataStoreManager
import com.example.weatherapp.domain.util.LocationDetail
import com.example.weatherapp.presention.view.OpeningScreen
import com.example.weatherapp.presention.view.WeatherScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var dataStoreManager : DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
       // dataStoreManager = DataStoreManager(this)

        setContent {
            //val navController = rememberNavController()
            WeatherApp()

        }
        fetchLocation()


    }

    private fun fetchLocation() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        dataStoreManager = DataStoreManager(this)

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let { location ->
                    lifecycleScope.launch{
                       // val result = dataStoreManager.saveToDataStore(location.latitude.toString().format("%.1d"),location.longitude.toString().format("%.1d"))
                        dataStoreManager.saveToDataStore(
                            LocationDetail(
                                latitude= location.latitude,
                                longitude = location.longitude
                            )
                        )
                    }
                    Toast.makeText(applicationContext, "lat: ${location.latitude} lon: ${location.longitude}", Toast.LENGTH_LONG).show()
                  //  Log.d("savelat: ", location.latitude.toString())
                  //  Log.d("savelat: ", location.longitude.toString())
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 101)
            return
        }

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    override fun onDestroy() {
        val dataStoreManager = DataStoreManager(this@MainActivity)
        CoroutineScope(Dispatchers.Main).launch {
            dataStoreManager.clearDataStore()
        }
        super.onDestroy()

    }
}

@Composable
fun WeatherApp() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "opening_screen") {
        composable("opening_screen") {
            OpeningScreen(navController)
        }
        composable(
            route= "weather_screen",

        ) {
            WeatherScreen(navController = navController)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        //WeatherApp()
    }
}