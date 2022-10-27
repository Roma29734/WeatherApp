package com.example.weatherapp.data.remote.repository

import com.example.weatherapp.data.remote.api.RetrofitInstance
import com.example.weatherapp.data.model.getOneCity.GetOneCity
import retrofit2.Response

class WeatherRepository {
    suspend fun getOneCity(city: String): Response<GetOneCity> {
        return RetrofitInstance.api.getOneCity(city)
    }

    suspend fun searchCity(city: String): Response<GetOneCity> {
        return RetrofitInstance.api.searchCity(city)
    }

}