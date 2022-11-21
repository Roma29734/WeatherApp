package com.example.weatherapp.data.local.dao

import androidx.room.*
import com.example.weatherapp.data.local.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addWeather(weather: Weather): Long

    @Update
    suspend fun updateLocal(weather: Weather)

    @Query("SELECT * FROM weather_table")
    fun readlLocalWeather(): Flow<List<Weather>>

    @Delete(entity = Weather::class)
    suspend fun deleteCity(weather: Weather)

    @Query("SELECT COUNT(*) FROM weather_table")
    suspend fun getSizeTable(): Int

    @Query("SELECT * FROM weather_table WHERE main = 1")
    fun getFavCity(): Weather

    @Query("UPDATE weather_table SET main = 0 WHERE id = :id")
    fun unselectedCity(id: Int)

    @Query("UPDATE weather_table SET main = 1 WHERE id = :id")
    fun selectCityz(id: Int)

    @Transaction
    suspend fun updateFavCity(id: Int) {
        unselectedCity(getFavCity().id)
        selectCityz(id)
    }
}