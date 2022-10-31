package com.example.weatherapp.domain

import com.example.weatherapp.domain.weatherUseCases.GetWeather

data class WeatherUseCases(
    val getWeather: GetWeather,
)