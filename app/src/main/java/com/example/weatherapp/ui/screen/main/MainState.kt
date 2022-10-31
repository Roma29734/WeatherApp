package com.example.weatherapp.ui.screen.main

import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.model.getOneCity.GetOneCity
import com.example.weatherapp.data.model.getSevenDayCity.SevenDayForeCast
import com.example.weatherapp.utils.LoadState

data class MainState(
    val savedCity: List<Weather> = listOf(),
    val selectedCity: Weather? = null,
    var loadState: LoadState = LoadState.SUCCESS,
    var successState: SevenDayForeCast? = null
)