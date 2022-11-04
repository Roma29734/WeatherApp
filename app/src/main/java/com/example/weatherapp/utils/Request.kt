package com.example.weatherapp.utils


sealed class Request<out R> {
    object Loading : Request<Nothing>()
    data class Success<out R>(internal val data: R) : Request<R>()
    data class Error(internal val error: Throwable?) : Request<Nothing>()
}