package com.example.weatherapp.domain.cityUserCase

import com.example.weatherapp.data.local.repository.LocalRepository
import javax.inject.Inject

class GetLocalCityCase @Inject constructor(private val repository: LocalRepository){
    operator fun invoke() = repository.readLocalWeather()
}

