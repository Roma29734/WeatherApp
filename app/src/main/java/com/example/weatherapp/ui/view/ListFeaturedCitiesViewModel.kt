package com.example.weatherapp.ui.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.domain.CityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListFeaturedCitiesViewModel @Inject constructor(
    private val cityUseCases: CityUseCases,
    private val localRepository: LocalRepository
): ViewModel() {

    private val _cities: Flow<List<Weather>> = cityUseCases.getLocalCityCase.invoke()
    val cities get() = _cities.asLiveData()

    fun setClick(city: Weather) {
        viewModelScope.launch(Dispatchers.IO) {
            cityUseCases.updateSelectedCityCase.invoke(city)
        }
    }

    fun setClickTest(city: Weather) {
        viewModelScope.launch(Dispatchers.IO) {
            localRepository.updateFavCity(city.id)
        }
    }

    fun deleteCity(city: Weather) {
        viewModelScope.launch {
            cityUseCases.deleteLocalCityCase.invoke(city)
        }
    }
}