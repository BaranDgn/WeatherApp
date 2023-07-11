package com.example.weatherapp.data.service

import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.domain.util.Resource
import retrofit2.http.GET

interface WeatherApiService {

    //https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&appid=8ddadecc7ae4f56fee73b2b405a63659
    @GET("data/2.5/onecall?lat=33.44&lon=-94.04&appid=8ddadecc7ae4f56fee73b2b405a63659")
    suspend fun getWeatherData() : Weather
}