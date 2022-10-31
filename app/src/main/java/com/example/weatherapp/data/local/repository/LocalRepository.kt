package com.example.weatherapp.data.local.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.local.Weather
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LocalRepository {
    fun readLocalWeather(): Flow<List<Weather>>

    suspend fun addWeather(weather: Weather)

    suspend fun updateWeather(weather: Weather)

    suspend fun deleteWeather(weather: Weather)
}