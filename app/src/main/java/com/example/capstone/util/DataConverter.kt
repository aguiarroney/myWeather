package com.example.capstone.util

import com.example.capstone.local.LocationDatabaseEntities
import com.example.capstone.model.WeatherModel

fun WeatherModel.asDataBaseModel(): LocationDatabaseEntities {
    return LocationDatabaseEntities(
        id = 0,
        cityName = this.name,
        country = this.sys.country,
        latitude = this.coord.lat,
        longitude = this.coord.lon,
        temperature = this.main.temp.toInt(),
        maxTemperature = this.main.temp.toInt(),
        minTemperature = this.main.temp.toInt(),
        feelsLike = this.main.feels_like.toInt(),
        weatherDescriotion = this.weather[0].description
    )
}