package com.example.weatherapp.ui.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.dao.WeatherDao
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.data.model.getOneCity.GetOneCity
import com.example.weatherapp.data.remote.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val repository: WeatherRepository
): ViewModel() {

    val oneCity: MutableLiveData<Response<GetOneCity>> = MutableLiveData()
    var localCity: LiveData<Weather> = localRepository.readLocalWeather

    fun getOneCity() {
        viewModelScope.launch {
            oneCity.value =  repository.getOneCity("Киев")
        }
    }

    fun updateLocalData(weather: Weather) {
        if(localCity.value == null) {
            viewModelScope.launch {
                localRepository.addWeather(weather)
            }
        }
        viewModelScope.launch {
            localRepository.updateWeather(weather)
        }
    }
}