package com.example.weatherapp.data.local.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.dao.WeatherDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class LocalRepositortImpl @Inject constructor(
    private val dao: WeatherDao
): LocalRepository {
    override fun readLocalWeather() = dao.readlLocalWeather()

    override suspend fun addWeather(weather: Weather): Long {
        return dao.addWeather(weather)
    }

    override suspend fun updateWeather(weather: Weather) {
        dao.updateLocal(weather)
        Log.d("repositoryLocal","Вызвал обновление данных")
    }

    override suspend fun deleteWeather(weather: Weather) {
        dao.deleteCity(weather)
        Log.d("searchProblemDelete","удаляю в репазитории")
    }

    override suspend fun getSizeTable(): Int {
       return dao.getSizeTable()
    }

    override suspend fun updateFavCity(id: Int) {
        dao.updateFavCity(id)
    }
}