package com.example.weatherapp.ui.screen.start.selectCity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.data.model.search.Search
import com.example.weatherapp.data.remote.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SelectedViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val localRepository: LocalRepository,
): ViewModel() {


    val searchResult: MutableLiveData<Response<Search>> = MutableLiveData()

    fun searchWeather(query: String) {
        val search = "%$query%"
        if(search.isEmpty()) return
        viewModelScope.launch {
//            searchResult.value = repository.searchCity(query)
        }
    }

    fun addToDataBase(weather: Weather) {
        viewModelScope.launch {
            localRepository.addWeather(weather)
        }
    }
}