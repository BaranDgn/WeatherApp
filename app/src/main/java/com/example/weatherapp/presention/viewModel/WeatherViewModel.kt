package com.example.weatherapp.presention.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.repository.WeatherApiImpl
import com.example.weatherapp.domain.util.DataStoreManager
import com.example.weatherapp.domain.util.LocationDetail
import com.example.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel@Inject constructor(
    private val application: Application,
    private val repo : WeatherApiImpl
) : AndroidViewModel(application){

    suspend fun getWeatherInfo(lat: Double, lon :Double): Resource<Weather>{

        return repo.getWeatherData(lat, lon)
    }

    private var _lat = mutableStateOf(41.015137)
    var lat : State<Double> = _lat

    private var _lon = mutableStateOf(28.979530)
    var lon :State<Double> = _lon

    private val dataStoreManager = DataStoreManager(application)
    init{
        getDataStoreValues()
    }
    private fun getDataStoreValues(){

        CoroutineScope(Dispatchers.IO).launch {
            dataStoreManager.getFromDataStore().collect{
                withContext(Dispatchers.Main){
                    _lat.value = it.latitude
                    _lon.value = it.longitude
                  // Log.d("lat", it.latitude.toString())
                  //  Log.d("lon", it.longitude.toString())
                }
            }
        }
    }
}