package com.example.weatherapp.data.local.dao

import androidx.room.*
import com.example.weatherapp.data.local.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWeather(weather: Weather)

    @Update
    suspend fun updateLocal(weather: Weather)

    @Query("SELECT * FROM weather_table")
    fun readlLocalWeather(): Flow<List<Weather>>

    @Delete
    suspend fun deleteCity(city: Weather)
}