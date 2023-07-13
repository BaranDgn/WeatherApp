package com.example.weatherapp.data.model

data class Current(
    val dew_point: Double,
    val dt: Int,
    val feels_like: Double,
    val temp: Double,
    val weather: List<WeatherX>,
)