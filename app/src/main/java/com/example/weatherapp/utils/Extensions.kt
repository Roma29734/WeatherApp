package com.example.weatherapp.utils

import android.graphics.Color
import android.view.View
import com.example.weatherapp.R
import com.google.android.material.snackbar.Snackbar
import kotlin.math.roundToInt

fun Double.toCelsiusString(): String {
    return this.roundToInt().toString() + "°"
}

fun showShackBarNoInternetConnection(view: View) {
    Snackbar.make(view, R.string.no_internet, Snackbar.LENGTH_SHORT)
        .setTextColor(Color.BLACK)
        .setBackgroundTint(Color.WHITE)
        .show()
}