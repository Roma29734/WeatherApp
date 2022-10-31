package com.example.weatherapp.ui.screen.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.data.model.getOneCity.GetOneCity
import com.example.weatherapp.data.remote.repository.WeatherRepository
import com.example.weatherapp.domain.CityUseCases
import com.example.weatherapp.domain.WeatherUseCases
import com.example.weatherapp.utils.LoadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val cityUseCases: CityUseCases,
    private val weatherUseCases: WeatherUseCases,
    private val repository: WeatherRepository
): ViewModel() {

    val oneCity: MutableLiveData<Response<GetOneCity>> = MutableLiveData()
    var localCity: MutableLiveData<Weather> = MutableLiveData()
    private var getWeatherJob: Job? = null

    private var _mainState = MutableStateFlow(MainState())
    val mainState
        get() = _mainState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            cityUseCases.getLocalCityCase.invoke().collect {cities ->
                if(cities.isEmpty()) {
                    cityUseCases.addLocalCityCase(Weather(1, 35.0, "state", "Москва", true))
                }
                val selectedCity = cities.first()
                _mainState.update { it.copy(savedCity = cities, selectedCity = selectedCity) }
            }
        }
    }

    fun getWeatherConnectYes() {
        viewModelScope.launch(Dispatchers.IO) {
            _mainState.value.selectedCity?.let { city ->
                val receivedData = repository.getOneCity(city.location).body()
                _mainState.update { it.copy(loadState = LoadState.SUCCESS, successState = receivedData) }
                val update = receivedData?.let { Weather(1, it.current.temp_c, it.current.condition.text, it.location.name, true) }
                update?.let { cityUseCases.updateLocalCityCase(it) }
                _mainState.update { it.copy() }
                Log.d("mainViewModel","вызвано getWeatherConnectYes")
            }
        }
    }
}
