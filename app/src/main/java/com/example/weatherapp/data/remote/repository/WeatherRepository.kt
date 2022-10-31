package com.example.weatherapp.data.remote.repository

import com.example.weatherapp.data.remote.api.RetrofitInstance
import com.example.weatherapp.data.model.getOneCity.GetOneCity
import com.example.weatherapp.data.model.getSevenDayCity.SevenDayForeCast
import com.example.weatherapp.data.model.search.Search
import retrofit2.Response

class WeatherRepository {
    suspend fun getOneCity(city: String): Response<GetOneCity> {
        return RetrofitInstance.api.getOneCity(city)
    }

    suspend fun searchCity(city: String): Response<Search> {
        return RetrofitInstance.api.searchCity(city)
    }

    suspend fun getSevenDayCity(city: String): Response<SevenDayForeCast> {
        return RetrofitInstance.api.getSevenDayCity(city, 7)
    }
}