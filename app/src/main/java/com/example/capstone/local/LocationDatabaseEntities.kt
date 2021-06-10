package com.example.capstone.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_weathers")
data class LocationDatabaseEntities(
    @PrimaryKey(autoGenerate = true) val id: Long,
//    val locationWeather: WeatherModel
    val cityName: String,
//    val state: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val temperature: Int,
    val maxTemperature: Int,
    val minTemperature: Int,
    val feelsLike: Int,
    val weatherDescriotion: String
)