package com.example.capstone.API

import com.example.capstone.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/weather?")
    suspend fun fetchCurrentWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") unit: String
    ): Response<WeatherModel>

    @GET("img/wn/{iconName}@2x.png")
    suspend fun getchWeatherIcon(
        @Path("iconName") iconName: String
    ): Response<String>

}