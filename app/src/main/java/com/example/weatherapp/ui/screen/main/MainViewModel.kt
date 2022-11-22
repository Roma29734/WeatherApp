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
import com.example.weatherapp.domain.getWeatherCase.GetWeatherUserCase
import com.example.weatherapp.utils.LoadState
import com.example.weatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val cityUseCases: CityUseCases,
    private val localRepository: LocalRepository,
    private val getWeatherUserCase: GetWeatherUserCase,
): ViewModel() {

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

    suspend fun getSizeCity(): Boolean {
        return localRepository.getSizeTable() != 0
    }

    fun testGetWeather() {
        Log.d("testStartBag","Вм функция получения погоды")
        viewModelScope.launch {
            _mainState.value.selectedCity?.let { city ->
                Log.d("testStartBag","Вм вызвал getWeatherUserCase")
                getWeatherUserCase(city.location).collect { result ->
                    Log.d("testStartBag","Вм зашел в onEach")
                    when(result) {
                        is Resource.Success -> {
                            _mainState.update { it.copy(successState = result.data, loadState = LoadState.SUCCESS) }
                            Log.d("testStartBag","Вм выдал погоду/состояниее success")
                        }
                        is Resource.Error -> {
                            _mainState.update { it.copy(loadState = LoadState.ERROR) }
                        }
                        is Resource.Loading -> {
                            _mainState.update { it.copy(loadState = LoadState.LOADING) }
                        }
                    }
                }
            }
        }
    }
}
