package com.example.weatherapp.data.local.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.dao.WeatherDao
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class LocalRepositortImpl @Inject constructor(
    private val dao: WeatherDao
): LocalRepository {
    override fun readLocalWeather() = dao.readlLocalWeather()

    override suspend fun addWeather(weather: Weather) {
        dao.addWeather(weather)
    }

    override suspend fun updateWeather(weather: Weather) {
        dao.updateLocal(weather)
        Log.d("repasitoryLocal","Вызвал обновление данных")
    }

    override suspend fun deleteWeather(weather: Weather) {
        dao.deleteCity(weather)
    }
}