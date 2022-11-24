package com.example.weatherapp.domain.getWeatherCase

import com.example.weatherapp.data.model.getSevenDayCity.SevenDayForeCast
import com.example.weatherapp.data.remote.repository.WeatherRepository
import com.example.weatherapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetWeatherUserCase @Inject constructor(
    private val repository: WeatherRepository
) {
    operator fun invoke(city: String): Flow<Resource<SevenDayForeCast>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getSevenDayCity(city)
            emit(Resource.Success(result))
        } catch (e: IOException) {
            emit(Resource.Error("No ithernet Connetc"))
        } catch (e : Exception) {
            emit(Resource.Error("Exception $e"))
        }
    }
}