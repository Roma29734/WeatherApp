package com.example.weatherapp.domain.cityUserCase

import com.example.weatherapp.data.local.repository.LocalRepository
import javax.inject.Inject

class GetSizeLocalCityCase @Inject constructor(private val repository: LocalRepository) {
    suspend operator fun invoke() = repository.getSizeTable()
}