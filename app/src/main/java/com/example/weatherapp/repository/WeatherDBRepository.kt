package com.example.weatherapp.repository

import com.example.weatherapp.data.WeatherDao
import com.example.weatherapp.model.favourite.Favourite
import com.example.weatherapp.model.units.Units
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDBRepository @Inject constructor(private val dao: WeatherDao) {

    fun getFavourite(): Flow<List<Favourite>> = dao.getFavourite()

    suspend fun insertFavourite(favourite: Favourite) = dao.insertFavourite(favourite = favourite)

    suspend fun updateFavourite(favourite: Favourite) = dao.updateFavourite(favourite = favourite)

    suspend fun deleteAllFavourite() = dao.deleteAllFavourite()

    suspend fun deleteFavourite(favourite: Favourite) = dao.deleteFavourite(favourite = favourite)

    suspend fun getFavouriteById(city:String) = dao.getFavById(city)

    fun getUnits():Flow<List<Units>> = dao.getUnit()

    suspend fun insertUnit(units: Units) = dao.insertUnit(units)

    suspend fun updateUnits(units: Units) = dao.updateUnit(units)

    suspend fun deleteAllUnits() = dao.deleteAllUnits()

    suspend fun deleteUnits(units: Units) = dao.deleteUnits(units)

}