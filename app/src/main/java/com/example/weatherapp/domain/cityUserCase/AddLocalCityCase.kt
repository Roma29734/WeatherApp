package com.example.weatherapp.domain.cityUserCase

import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.repository.LocalRepository
import javax.inject.Inject

class AddLocalCityCase @Inject constructor(private val repository: LocalRepository){
    suspend operator fun invoke(weather: Weather) = repository.addWeather(weather)
}