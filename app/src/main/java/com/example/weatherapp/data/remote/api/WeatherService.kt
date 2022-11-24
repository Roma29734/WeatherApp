package com.example.weatherapp.data.remote.api

import com.example.weatherapp.data.model.getSevenDayCity.SevenDayForeCast
import com.example.weatherapp.data.model.search.Search
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

//    http://api.weatherapi.com/v1/current.json?key=7d7a314cf65f42b1884135900222610&q=London
//    https://api.weatherapi.com/v1/search.json?key=7d7a314cf65f42b1884135900222610&q=%D0%A1%D0%B0%D0%BD%D0%BA
//    http://api.weatherapi.com/v1/forecast.json?key=7d7a314cf65f42b1884135900222610&q=London&days=7
    @GET("v1/search.json?")
    suspend fun searchCity(
        @Query ("key") apiKey: String,
        @Query ("q") city: String
    ): Search

    @GET("v1/forecast.json?")
    suspend fun getSevenDayCity(
        @Query ("key") apiKey: String,
        @Query("q") city: String,
        @Query("days") days: Int
    ): SevenDayForeCast
}