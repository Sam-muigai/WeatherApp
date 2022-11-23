package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.WeatherDao
import com.example.weatherapp.data.WeatherDatabase
import com.example.weatherapp.network.WeatherApi
import com.example.weatherapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    @Singleton
    @Provides
    fun provideWeatherDao(weatherDatabase: WeatherDatabase):WeatherDao{
       return weatherDatabase.weatherDao()
    }
    @Singleton
    @Provides
    fun provideWeatherDatabase(@ApplicationContext context: Context):WeatherDatabase{
        return Room
            .databaseBuilder(context,WeatherDatabase::class.java,"weather_db")
            .fallbackToDestructiveMigration()
            .build()
    }
}