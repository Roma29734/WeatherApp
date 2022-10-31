package com.example.weatherapp.ui.screen.detail

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.data.model.getOneCity.GetOneCity
import com.example.weatherapp.data.remote.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val localRepository: LocalRepository,
): ViewModel() {

    val oneCity: MutableLiveData<Response<GetOneCity>> = MutableLiveData()

    fun getCity(query: String) {
        viewModelScope.launch {
            oneCity.value = repository.getOneCity(query)
        }
    }

    fun updateLocalData(city: Weather) {
        viewModelScope.launch {
            localRepository.updateWeather(city)
            Log.d("detailViewModel", "обновление бд")
        }
    }
}