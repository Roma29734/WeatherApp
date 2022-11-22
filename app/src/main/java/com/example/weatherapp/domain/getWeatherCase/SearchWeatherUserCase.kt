package com.example.weatherapp.domain.getWeatherCase

import com.example.weatherapp.data.model.search.Search
import com.example.weatherapp.data.remote.repository.WeatherRepository
import com.example.weatherapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SearchWeatherUserCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(city: String): Flow<Resource<Search>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.searchCity(city)
            emit(Resource.Success(result))
        } catch (e: IOException) {
            emit(Resource.Error("No ithernet Connetc"))
        } catch (e: Exception) {
            emit(Resource.Error("Exception $e"))
        }
    }
}