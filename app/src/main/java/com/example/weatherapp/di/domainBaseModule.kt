package com.example.weatherapp.di

import com.example.weatherapp.data.local.repository.LocalRepository
import com.example.weatherapp.data.remote.repository.WeatherRepository
import com.example.weatherapp.domain.WeatherUseCases
import com.example.weatherapp.domain.cityUserCase.*
import com.example.weatherapp.domain.getWeatherCase.GetWeatherUserCase
import com.example.weatherapp.domain.getWeatherCase.SearchWeatherUserCase
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
    fun provideGetSizeCityLocal(repository: LocalRepository) = GetSizeLocalCityCase(repository)

    @Singleton
    @Provides
    fun provideGetWeatheruserCase(repository: WeatherRepository) = GetWeatherUserCase(repository)

    @Singleton
    @Provides
    fun provideSearchWeatherCase(repository: WeatherRepository) = SearchWeatherUserCase(repository)


    @Singleton
    @Provides
    fun provideCityUseCases(
        getLocalCityCase: GetLocalCityCase,
        updateLocalCityCase: UpdateLocalCutyCase,
        addLocalCityCase: AddLocalCityCase,
        deleteLocalCityCase: DeleteLocalCityCase,
        updateSelectedCityCase: UpdateSelectedCityCase,
        getSizeLocalCityCase: GetSizeLocalCityCase,
        getWeatherUserCase: GetWeatherUserCase,
        searchWeatherUserCase: SearchWeatherUserCase,
    ) = WeatherUseCases(getLocalCityCase, updateLocalCityCase, addLocalCityCase, deleteLocalCityCase, updateSelectedCityCase, getSizeLocalCityCase, getWeatherUserCase, searchWeatherUserCase)
}