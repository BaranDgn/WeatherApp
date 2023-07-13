package com.example.weatherapp.data.model

data class Daily(
    val dt: Int,
    //val feels_like: FeelsLike,
    val temp: Temp,
    val weather: List<WeatherX>,
)