package com.example.weatherapp.data.repository

import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherX
import com.example.weatherapp.data.service.WeatherApiService
import com.example.weatherapp.domain.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

//@ActivityScoped
class WeatherApiImpl@Inject constructor(
    private var api: WeatherApiService
) {

    suspend fun getWeatherData(lat: Double, lon :Double) :Resource<Weather>{
         val response = try {
             api.getWeatherData(lat, lon)
        }catch (e: java.lang.Exception){
            return Resource.Error("")
        }
        return Resource.Success(response)
    }


}