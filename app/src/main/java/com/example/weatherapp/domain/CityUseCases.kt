package com.example.weatherapp.domain

import com.example.weatherapp.domain.cityUserCase.AddLocalCityCase
import com.example.weatherapp.domain.cityUserCase.DeleteLocalCityCase
import com.example.weatherapp.domain.cityUserCase.GetLocalCityCase
import com.example.weatherapp.domain.cityUserCase.UpdateLocalCutyCase

data class CityUseCases (
   val getLocalCityCase: GetLocalCityCase,
   val updateLocalCityCase: UpdateLocalCutyCase,
   val addLocalCityCase: AddLocalCityCase,
   val deleteLocalCityCase: DeleteLocalCityCase,
)