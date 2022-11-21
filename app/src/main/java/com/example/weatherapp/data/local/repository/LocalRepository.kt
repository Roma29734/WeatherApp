package com.example.weatherapp.data.local.repository

import com.example.weatherapp.data.local.Weather
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun readLocalWeather(): Flow<List<Weather>>

    suspend fun addWeather(weather: Weather): Long

    suspend fun updateWeather(weather: Weather)

    suspend fun deleteWeather(weather: Weather)

    suspend fun getSizeTable(): Int

    suspend fun updateFavCity(id: Int)
}