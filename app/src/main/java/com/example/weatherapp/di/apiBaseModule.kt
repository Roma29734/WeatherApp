package com.example.weatherapp.di

import com.example.weatherapp.data.remote.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class apiBaseModule {
    @Provides
    fun provideWeatherRepository(): WeatherRepository = WeatherRepository()
}