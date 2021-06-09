package com.example.capstone.repository

import android.util.Log
import com.example.capstone.API.WeatherAPI
import com.example.capstone.model.WeatherModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    companion object {

        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = "3bda3d145ab8ea59bc74e3db0204ea61"

        val okHTTPClient: OkHttpClient.Builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                okHTTPClient.addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .baseUrl(BASE_URL)
            .build()

        val weatherAPI = retrofit.create(WeatherAPI::class.java)
    }

    suspend fun fetchCurrentWeather(latitude: Double, longitude: Double): WeatherModel {

        lateinit var temperature: WeatherModel

        val response = weatherAPI.fetchCurrentWeather(latitude, longitude, API_KEY, "metric")

        if (response.isSuccessful) {

            response.body()?.let {
                temperature = it
            }

        } else
            Log.i("Response erro", "${response.errorBody()}")

        return temperature
    }

    suspend fun fetchWeatherByLocationName(
        city: String,
        state: String,
        country: String
    ): WeatherModel? {

        lateinit var temperature: WeatherModel

        val response = weatherAPI.fetchWeatherByLocationName("$city,$state,$country", API_KEY)

        if (response.isSuccessful) {
            response.body()?.let {
                temperature = it
            }
        } else {
            Log.i("fetchweatherBy", "${response.errorBody()}")
        }

        return null
    }

}