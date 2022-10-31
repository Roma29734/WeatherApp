package com.example.weatherapp.domain.weatherUseCases

import com.example.weatherapp.data.remote.repository.WeatherRepository
import javax.inject.Inject

class GetWeather @Inject constructor(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String) = repository.getOneCity(city)
}