package com.example.weatherapp.domain.util

import javax.inject.Singleton

class LocationSingleton private constructor() {

    companion object{

        @Volatile
        private var instance : LocationSingleton ?= null

        fun getLocation() : LocationSingleton =
            instance ?: synchronized(this){
                instance ?: LocationSingleton().also { instance = it }
            }
    }

    var latitude : Double = 0.0
    var longitude : Double = 0.0
    var locationName: String = ""

}