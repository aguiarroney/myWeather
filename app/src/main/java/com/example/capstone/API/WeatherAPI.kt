package com.example.capstone.API

import com.example.capstone.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/weather?")
    suspend fun fetchCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") unit: String
    ): Response<WeatherModel>

}