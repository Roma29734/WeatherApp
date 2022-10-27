package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.local.WeatherDataBase
import com.example.weatherapp.data.local.dao.WeatherDao
import com.example.weatherapp.data.local.repository.LocalRepositortImpl
import com.example.weatherapp.data.local.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class localBaseModule {

    @Provides
    fun provideRepositiry(impl: LocalRepositortImpl): LocalRepository = impl

    @Provides
    fun provideWeatherDao(appDataBase: WeatherDataBase): WeatherDao = appDataBase.weatherDao()

    @Provides
    @Singleton
    fun provideMovieDataBase(@ApplicationContext appContext: Context): WeatherDataBase =
        Room.databaseBuilder(
            appContext,
            WeatherDataBase::class.java,
            "weather_table"
        ).build()
}