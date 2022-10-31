package com.example.weatherapp.data.remote.api

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.model.getOneCity.GetOneCity
import com.example.weatherapp.data.model.search.Search
import com.example.weatherapp.data.model.search.SearchItem
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {

//    http://api.weatherapi.com/v1/current.json?key=7d7a314cf65f42b1884135900222610&q=London
//    https://api.weatherapi.com/v1/search.json?key=7d7a314cf65f42b1884135900222610&q=%D0%A1%D0%B0%D0%BD%D0%BA

    @GET("v1/current.json?key=7d7a314cf65f42b1884135900222610")
    suspend fun getOneCity(
        @Query ("q") city: String
    ): Response<GetOneCity>

    @GET("v1/search.json?key=7d7a314cf65f42b1884135900222610")
    suspend fun searchCity(
        @Query ("q") city: String
    ): Response<Search>

}