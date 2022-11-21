package com.example.weatherapp.ui.screen.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.data.remote.repository.WeatherRepository
import com.example.weatherapp.domain.CityUseCases
import com.example.weatherapp.utils.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val cityUseCases: CityUseCases,
    private val repository: WeatherRepository,
    private val localRepository: LocalRepository,
): ViewModel() {

    private var getWeatherJob: Job? = null

    private var _mainState = MutableStateFlow(MainState())
    val mainState
        get() = _mainState.asStateFlow()

        init {
        viewModelScope.launch(Dispatchers.IO) {
            cityUseCases.getLocalCityCase.invoke().collect {cities ->
                if(cities.isEmpty()) {
                    return@collect
                }
                Log.d("testStartBag","Вм получение локальных данных")
                val selectedCity = cities.first {it.main}
                Log.d("mainViewModel",selectedCity.location)
                _mainState.update { it.copy(savedCity = cities, selectedCity = selectedCity) }
            }
        }
    }

    fun getWeatherConnectYes() {
        viewModelScope.launch {
            _mainState.value.selectedCity?.let { city ->
                Log.d("testStartBag","ВМ получение данных из api")
                _mainState.update { it.copy(loadState = LoadState.LOADING) }
                val receivedData = repository.getSevenDayCity(city.location)
                if(receivedData.isSuccessful) {
                    _mainState.update { it.copy(loadState = LoadState.SUCCESS, successState = receivedData.body()) }
//                    val update = receivedData.body()?.let { Weather(1, it.current.temp_c, it.current.condition.text, it.location.name, city.main) }
//                    Log.d("mainViewModel","обновлены данные")
//                    Log.d("testStartBag","ВМ обновление локальныъх данных")
//                    update?.let { cityUseCases.updateLocalCityCase(it) }
                } else {
                    _mainState.update { it.copy(loadState = LoadState.ERROR) }
                }
                Log.d("mainViewModel","вызвано getWeatherConnectYes")
            }
        }
    }

    suspend fun getSizeCity(): Boolean {
        return localRepository.getSizeTable() != 0
    }
}
