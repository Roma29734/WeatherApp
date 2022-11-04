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

    @Query("UPDATE weather_table SET main = 0 WHERE id != :id")
    fun setUnselected(id: Int)

    @Query("UPDATE weather_table SET main = 1 WHERE id = :id")
    fun setSelected(id: Int)

    @Transaction
    suspend fun updateSelectedCity(id: Int) {
        setUnselected(id)
        setSelected(id)
    }
}