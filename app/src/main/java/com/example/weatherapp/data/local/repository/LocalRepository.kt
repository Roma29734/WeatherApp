package com.example.weatherapp.data.local.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.local.Weather

interface LocalRepository {
    val readLocalWeather: LiveData<Weather>

    suspend fun addWeather(weather: Weather)

    suspend fun updateWeather(weather: Weather)
}