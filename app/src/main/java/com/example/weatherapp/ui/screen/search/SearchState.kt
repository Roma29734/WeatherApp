package com.example.weatherapp.ui.screen.search

import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.model.getSevenDayCity.SevenDayForeCast
import com.example.weatherapp.data.model.search.Search
import com.example.weatherapp.utils.LoadState

data class SearchState(
    var loadState: LoadState = LoadState.SUCCESS,
    var successState: Search? = null
)
