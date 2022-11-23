package com.example.weatherapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.model.favourite.Favourite
import com.example.weatherapp.model.units.Units

@Database(entities = [Favourite::class,Units::class], version = 2, exportSchema = false)
abstract class WeatherDatabase:RoomDatabase() {
    abstract fun weatherDao():WeatherDao
}