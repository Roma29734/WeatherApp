package com.example.weatherapp.ui.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.domain.WeatherUseCases
import com.example.weatherapp.utils.SaveShared
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListFeaturedCitiesViewModel @Inject constructor(
    application: Application,
    private val weatherUseCases: WeatherUseCases,
): AndroidViewModel(application) {

    val context get() = getApplication<Application>()

    private val _cities: Flow<List<Weather>> = weatherUseCases.getLocalCityCase.invoke()
    val cities get() = _cities.asLiveData()

    fun setClick(city: Weather) {
        viewModelScope.launch(Dispatchers.IO) {
            weatherUseCases.updateSelectedCityCase(city)
        }
    }

    fun deleteCity(city: Weather) {
        viewModelScope.launch {
            weatherUseCases.deleteLocalCityCase(city)
        }
        changeStateCity(city.location, false)
    }

    private fun changeStateCity(city: String, state: Boolean) {
        SaveShared.setFavorite(context, city, state)
    }
}