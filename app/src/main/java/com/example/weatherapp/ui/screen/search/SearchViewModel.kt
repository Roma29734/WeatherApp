package com.example.weatherapp.ui.screen.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.data.model.search.Search
import com.example.weatherapp.data.model.search.SearchItem
import com.example.weatherapp.data.remote.repository.WeatherRepository
import com.example.weatherapp.domain.WeatherUseCases
import com.example.weatherapp.domain.getWeatherCase.SearchWeatherUserCase
import com.example.weatherapp.ui.screen.main.MainState
import com.example.weatherapp.utils.LoadState
import com.example.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val weatherUserCase: WeatherUseCases,
): ViewModel() {

    private var _searchResult = MutableStateFlow(SearchState())
    val searchResult get() = _searchResult.asStateFlow()


    fun searchWeather(query: String) {
        val search = "%$query%"
        viewModelScope.launch {
            weatherUserCase.searchWeatherUserCase(search).collect {result ->
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
}