package com.example.weatherapp.ui.screen.detail

import android.app.Application
import androidx.lifecycle.*
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.data.model.getSevenDayCity.SevenDayForeCast
import com.example.weatherapp.data.remote.repository.WeatherRepository
import com.example.weatherapp.domain.WeatherUseCases
import com.example.weatherapp.utils.LoadState
import com.example.weatherapp.utils.Resource
import com.example.weatherapp.utils.SaveShared
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application,
    private val weatherUseCases: WeatherUseCases,
): AndroidViewModel(application) {

    val context get() = getApplication<Application>()

    private var _oneCity = MutableStateFlow(DetailState())
    val oneCity get() = _oneCity

    fun getCity(query: String) {
        viewModelScope.launch {
            weatherUseCases.getWeatherUserCase(query).collect {result ->
                when(result) {
                    is Resource.Error -> {
                        _oneCity.update { it.copy(loadState = LoadState.ERROR) }
                    }
                    is Resource.Success -> {
                        _oneCity.update { it.copy(loadState = LoadState.SUCCESS, successState = result.data) }
                    }
                    is Resource.Loading -> {
                        _oneCity.update { it.copy(loadState = LoadState.LOADING) }
                    }
                }
            }
        }
    }

    fun getStateCity(city: String): Boolean {
        return SaveShared.getFavorite(context, city)
    }

    fun addFavData(city: Weather) {
        viewModelScope.launch {
            weatherUseCases.addLocalCityCase(city)
        }
        changeStateCity(city.location, true)
    }

    private fun changeStateCity(city: String, state: Boolean) {
        SaveShared.setFavorite(context, city, state)
    }
}