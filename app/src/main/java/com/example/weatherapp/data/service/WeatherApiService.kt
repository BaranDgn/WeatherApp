package com.example.weatherapp.data.service

import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherX
import com.example.weatherapp.domain.util.Constants.API_KEY
import com.example.weatherapp.domain.util.Resource
import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import retrofit2.http.Url

interface WeatherApiService {

    //https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&appid=8ddadecc7ae4f56fee73b2b405a63659

    //https://api.openweathermap.org/data/2.5/ --> Base Url
    // onecall?lat=33.44&lon=-94.04&exclude=hourly,minutely,alerts&appid=  --> middle part
    // 8ddadecc7ae4f56fee73b2b405a63659  --> Api key
    /*
    @GET("onecall?lat={lat}&lon={lon}&exclude=hourly,minutely,alerts&appid=$API_KEY")
    suspend fun getWeatherData(
        @Path("lat") lat : Double,
        @Path("lon") lon : Double
    ) : Weather
    */


    @GET("onecall")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "hourly,minutely,alerts",
        @Query("appid") appid: String = API_KEY,
    ): Weather

    // https://openweathermap.org/img/wn/10n@2x.png
}