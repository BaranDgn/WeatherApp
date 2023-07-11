package com.example.weatherapp.presenter.viewModel

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.repository.WeatherApiImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel@Inject constructor(
    private val repo : WeatherApiImpl
) : ViewModel(){



}