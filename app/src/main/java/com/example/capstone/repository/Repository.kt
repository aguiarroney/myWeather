package com.example.capstone.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.example.capstone.API.WeatherAPI
import com.example.capstone.model.WeatherModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class Repository {

    companion object {

        const val BASE_URL = "https://api.openweathermap.org/"
        const val API_KEY = "3bda3d145ab8ea59bc74e3db0204ea61"

        private val okHTTPClient: OkHttpClient.Builder = OkHttpClient.Builder()
        private val loggingInterceptor = HttpLoggingInterceptor()
        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                okHTTPClient.addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            )
            .baseUrl(BASE_URL)
            .build()

        val weatherAPI: WeatherAPI = retrofit.create(WeatherAPI::class.java)
    }

    suspend fun fetchCurrentWeather(latitude: Double, longitude: Double): Response<WeatherModel>? {

        return try {
            weatherAPI.fetchCurrentWeather(latitude, longitude, API_KEY, "metric")

        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchWeatherByLocationName(
        city: String,
        state: String,
        country: String
    ): Response<WeatherModel>? {

        return try {
            weatherAPI.fetchWeatherByLocationName("$city,$state,$country", API_KEY, "metric")
        } catch (e: Exception) {
            null
        }
    }

}