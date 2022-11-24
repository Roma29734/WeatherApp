package com.example.weatherapp.domain

import com.example.weatherapp.domain.cityUserCase.*
import com.example.weatherapp.domain.getWeatherCase.GetWeatherUserCase
import com.example.weatherapp.domain.getWeatherCase.SearchWeatherUserCase

data class WeatherUseCases (
   val getLocalCityCase: GetLocalCityCase,
   val updateLocalCityCase: UpdateLocalCutyCase,
   val addLocalCityCase: AddLocalCityCase,
   val deleteLocalCityCase: DeleteLocalCityCase,
   val updateSelectedCityCase: UpdateSelectedCityCase,
   val getSizeLocalCityCase: GetSizeLocalCityCase,
   val getWeatherUserCase: GetWeatherUserCase,
   val searchWeatherUserCase: SearchWeatherUserCase,
)