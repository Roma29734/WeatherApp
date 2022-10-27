package com.example.weatherapp.data.local.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.dao.WeatherDao
import javax.inject.Inject

class LocalRepositortImpl @Inject constructor(
    private val dao: WeatherDao
): LocalRepository {
    override val readLocalWeather: LiveData<Weather>
        get() = dao.readlLocalWeather()

    override suspend fun addWeather(weather: Weather) {
        dao.addWeather(weather)
    }

    override suspend fun updateWeather(weather: Weather) {
        dao.updateLocal(weather)
    }

}