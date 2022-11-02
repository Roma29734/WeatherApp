package com.example.weatherapp.ui.screen.favourite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.Weather
import com.example.weatherapp.data.local.repository.LocalRepositortImpl
import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.domain.CityUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repositortImpl: LocalRepository,
    private val cityUseCases: CityUseCases,
): ViewModel() {

    val getFavouriteCity: MutableLiveData<List<Weather>> = MutableLiveData()

    init {
        viewModelScope.launch {
            cityUseCases.getLocalCityCase.invoke().collect {cities ->
                getFavouriteCity.value  = cities
            }
        }
    }
}