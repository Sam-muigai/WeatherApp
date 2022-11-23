package com.example.weatherapp.screens.mainscreen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository)
    :ViewModel() {
  suspend fun getAllWeather(city: String, units: String):
          DataOrException<Weather,Boolean,Exception>{
      return repository.getAllWeather(city,units)
  }
}