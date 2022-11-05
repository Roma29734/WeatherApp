package com.example.weatherapp.di

import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.data.remote.repository.WeatherRepository
import com.example.weatherapp.domain.CityUseCases
import com.example.weatherapp.domain.cityUserCase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class domainBaseModule {

    @Singleton
    @Provides
    fun provideGetLocalCityCase(repository: LocalRepository) = GetLocalCityCase(repository)

    @Singleton
    @Provides
    fun provideUpdateLocalCityCase(repository: LocalRepository) = UpdateLocalCutyCase(repository)

    @Singleton
    @Provides
    fun provideAddLocalCityCase(repository: LocalRepository) = AddLocalCityCase(repository)

    @Singleton
    @Provides
    fun provideDeleteLocalCityCase(repository: LocalRepository) = DeleteLocalCityCase(repository)

    @Singleton
    @Provides
    fun provideUpdateSelectedCity(repository: LocalRepository) = UpdateSelectedCityCase(repository)

    @Singleton
    @Provides
    fun provideCityUseCases(
        getLocalCityCase: GetLocalCityCase,
        updateLocalCityCase: UpdateLocalCutyCase,
        addLocalCityCase: AddLocalCityCase,
        deleteLocalCityCase: DeleteLocalCityCase,
        updateSelectedCityCase: UpdateSelectedCityCase
    ) = CityUseCases(getLocalCityCase, updateLocalCityCase, addLocalCityCase, deleteLocalCityCase, updateSelectedCityCase)
}