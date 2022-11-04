package com.example.weatherapp.domain

import com.example.weatherapp.domain.cityUserCase.*

data class CityUseCases (
   val getLocalCityCase: GetLocalCityCase,
   val updateLocalCityCase: UpdateLocalCutyCase,
   val addLocalCityCase: AddLocalCityCase,
   val deleteLocalCityCase: DeleteLocalCityCase,
   val updateSelectedCityCase: UpdateSelectedCityCase,
)