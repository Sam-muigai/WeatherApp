package com.example.weatherapp.repository

import android.util.Log
import com.example.weatherapp.data.DataOrException
import com.example.weatherapp.model.Weather
import com.example.weatherapp.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api:WeatherApi) {
    suspend fun getAllWeather(cityName: String, units: String):
            DataOrException<Weather,Boolean,Exception>{
        val response =
            try {
                api.getWeather(cityName,units)
            }catch (e:Exception){
                 return DataOrException(e = e)
            }
        Log.d("INSIDE","getWeather: $response")
        return DataOrException(data = response)
    }
}