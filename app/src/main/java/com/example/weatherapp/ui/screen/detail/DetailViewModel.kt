package com.example.weatherapp.ui.screen.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.data.model.getSevenDayCity.SevenDayForeCast
import com.example.weatherapp.data.remote.repository.WeatherRepository
import com.example.weatherapp.domain.CityUseCases
import com.example.weatherapp.utils.SaveShared
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    application: Application,
    private val repository: WeatherRepository,
    private val localRepository: LocalRepository,
    private val cityUseCases: CityUseCases,
): AndroidViewModel(application) {

    val context get() = getApplication<Application>()

    val oneCity: MutableLiveData<Response<SevenDayForeCast>> = MutableLiveData()

    fun getCity(query: String) {
        viewModelScope.launch {
            oneCity.value = repository.getSevenDayCity(query)
        }
    }

    fun getStateCity(city: String): Boolean {
        return SaveShared.getFavorite(context, city)
    }

    fun addFavData(city: Weather) {
        viewModelScope.launch {
            cityUseCases.addLocalCityCase(city)
        }
        changeStateCity(city.location, true)
    }

    fun deleteFavData(city: Weather) {
        viewModelScope.launch {
            localRepository.deleteWeather(city)
        }
        changeStateCity(city.location, false)
    }

    private fun changeStateCity(city: String, state: Boolean) {
        SaveShared.setFavorite(context, city, state)
    }
}