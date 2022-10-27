package com.example.weatherapp.data.remote.api

import com.example.weatherapp.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }
}