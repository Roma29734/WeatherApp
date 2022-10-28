package com.example.weatherapp.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "weather_table")
data class Weather (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")val id: Int,
    @ColumnInfo(name = "degree")val degree: Double,
    @ColumnInfo(name = "condition")val condition: String,
    @ColumnInfo(name = "location")val location: String,
): Parcelable
