package com.example.weatherapp.ui.screen.detail

import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.model.getSevenDayCity.SevenDayForeCast
import com.example.weatherapp.utils.LoadState

data class DetailState(
    var loadState: LoadState = LoadState.SUCCESS,
    var successState: SevenDayForeCast? = null
)