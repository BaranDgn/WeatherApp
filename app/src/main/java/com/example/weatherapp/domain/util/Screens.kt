package com.example.weatherapp.domain.util

sealed class Screens(val route: String){
    object HomeScreen: Screens("home_screen")
    object WeatherScreen: Screens("weather_screen")
}
