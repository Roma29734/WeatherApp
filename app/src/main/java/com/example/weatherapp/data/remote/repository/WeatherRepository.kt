package com.example.weatherapp.data.remote.repository

import com.example.weatherapp.data.remote.api.RetrofitInstance
import com.example.weatherapp.data.model.getSevenDayCity.SevenDayForeCast
import com.example.weatherapp.data.model.search.Search
import com.example.weatherapp.utils.API_KEY
import retrofit2.Response

class WeatherRepository {

    suspend fun searchCity(city: String): Search {
        return RetrofitInstance.api.searchCity(city = city, apiKey = API_KEY)
    }

    suspend fun getSevenDayCity(city: String): SevenDayForeCast {
        return RetrofitInstance.api.getSevenDayCity(city = city, days = 7, apiKey = API_KEY)
    }
}