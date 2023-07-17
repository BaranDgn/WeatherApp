package com.example.weatherapp.presention.viewModel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.repository.WeatherApiImpl
import com.example.weatherapp.domain.util.LocationSingleton
import com.example.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel@Inject constructor(
    private val application: Application,
    private val repo : WeatherApiImpl
) : AndroidViewModel(application){

   /* suspend fun getWeatherInfo(): Resource<Weather> {
        var result: Resource<Weather>? = null // Initialize the result variable

        dataStoreManager.getFromDataStore().collect { locationDetail ->
            if (locationDetail.latitude != null && locationDetail.longitude != null) {
                val loc = repo.getWeatherData(locationDetail.latitude, locationDetail.longitude)
                result = Resource.Success(loc).data // Store the result
            } else {
                result = Resource.Error("Location details not available") // Store the error result
            }
        }

        return result ?: Resource.Error("Failed to retrieve location details") // Return the result or a default error
    }*

    */

    suspend fun getWeatherInfo(): Resource<Weather> {
        val locationSingleton = LocationSingleton.getLocation()
        val lat = locationSingleton.latitude
        val lon = locationSingleton.longitude

        return if (lat != 0.0 && lon != 0.0) {
            repo.getWeatherData(lat, lon)
        } else {
            Resource.Error("Location details not available")
        }
    }
    //private var _lat = mutableStateOf(41.015137)
    //var lat : State<Double> = _lat

    //private var _lon = mutableStateOf(28.979530)
    //var lon :State<Double> = _lon

    //private val dataStoreManager = DataStoreManager(application)
    init{
        //getDataStoreValues()
    }

    /*private fun getDataStoreValues(){
        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.getFromDataStore().collect{
                if(it.latitude != null && it.longitude != null){
                    withContext(Dispatchers.Main){

                        _lat.value = it.latitude
                        _lon.value = it.longitude
                        // Log.d("lat", it.latitude.toString())
                        //  Log.d("lon", it.longitude.toString())
                    }
                }

            }
        }
    }*/
}