package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.weatherapp.domain.util.DeepLinkPattern
import com.example.weatherapp.domain.util.LocationSingleton
import com.example.weatherapp.domain.util.Screens
import com.example.weatherapp.presention.view.OpeningScreen
import com.example.weatherapp.presention.view.WeatherScreen
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.google.android.gms.location.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
   //private lateinit var dataStoreManager : DataStoreManager

    //private lateinit var locationSingleton : LocationSingleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            val navController = rememberNavController()
            WeatherApp()
        }

        //fetchLocation()
    }

    private fun fetchLocation() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        //dataStoreManager = DataStoreManager(this)

        val geocoder = Geocoder(this)
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult?.lastLocation?.let { location ->
                    CoroutineScope(Dispatchers.IO).launch{
                        val addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val address  = addressList!!.firstOrNull()
                        val city = address?.locality ?: address?.adminArea
                        val countryCode = address?.countryCode
                        val locationName = "$city, $countryCode"

                        // val result = dataStoreManager.saveToDataStore(location.latitude.toString().format("%.1d"),location.longitude.toString().format("%.1d"))
                       /* dataStoreManager.saveToDataStore(
                            LocationDetail(
                                latitude= location.latitude,
                                longitude = location.longitude,
                            )
                        )*/
                        val locationSingleton = LocationSingleton.getLocation()
                        locationSingleton.latitude = location.latitude
                        locationSingleton.longitude = location.longitude
                        locationSingleton.locationName = locationName ?: ""
                    }
                   // locationSingleton = LocationDetail



                    Toast.makeText(applicationContext, "lat: ${location.latitude} lon: ${location.longitude}", Toast.LENGTH_LONG).show()
                    //Toast.makeText(applicationContext, "Location: $locationName", Toast.LENGTH_LONG).show()


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

    override fun onResume() {
        super.onResume()
        fetchLocation()
    }
/*
    override fun onDestroy() {
        val dataStoreManager = DataStoreManager(this@MainActivity)
        CoroutineScope(Dispatchers.Main).launch {
            dataStoreManager.clearDataStore()
        }
        super.onDestroy()
    }
    */
}

@Composable
fun WeatherApp() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.HomeScreen.route) {
        composable(Screens.HomeScreen.route,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = DeepLinkPattern.HomePattern
                }
            )
        ) {
            OpeningScreen(navController)
        }
        composable(
            route= Screens.WeatherScreen.route,

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