package com.example.weatherapp.presenter.viewModel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.model.Weather
import com.example.weatherapp.data.model.WeatherX
import com.example.weatherapp.data.repository.WeatherApiImpl
import com.example.weatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel@Inject constructor(
    private val repo : WeatherApiImpl
) : ViewModel(){

    suspend fun getWeatherInfo(): Resource<Weather>{
        return repo.getWeatherData()
    }


}