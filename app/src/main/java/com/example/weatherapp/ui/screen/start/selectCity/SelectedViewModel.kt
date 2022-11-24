package com.example.weatherapp.ui.screen.start.selectCity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.data.model.search.Search
import com.example.weatherapp.data.remote.repository.WeatherRepository
import com.example.weatherapp.domain.WeatherUseCases
import com.example.weatherapp.ui.screen.search.SearchState
import com.example.weatherapp.utils.LoadState
import com.example.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SelectedViewModel @Inject constructor(
    private val weatherUseCases: WeatherUseCases,
): ViewModel() {


    private var _searchResult = MutableStateFlow(SearchState())
    val searchResult get() = _searchResult

    fun searchWeather(query: String) {
        val search = "%$query%"
        if(search.isEmpty()) return
        viewModelScope.launch {
            weatherUseCases.searchWeatherUserCase(search).collect {result ->
                when (result) {
                    is Resource.Loading -> {
                        _searchResult.update { it.copy(loadState = LoadState.LOADING) }
                    }
                    is Resource.Success -> {
                        _searchResult.update { it.copy(loadState = LoadState.SUCCESS, successState = result.data) }
                    }

                    is Resource.Error -> {
                        _searchResult.update { it.copy(loadState = LoadState.ERROR) }
                    }
                }
            }
        }
    }

    fun addToDataBase(weather: Weather) {
        viewModelScope.launch {
            weatherUseCases.addLocalCityCase(weather)
        }
    }
}