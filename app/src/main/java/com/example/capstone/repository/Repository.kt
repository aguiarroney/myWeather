package com.example.capstone.repository

import android.util.Log
import com.example.capstone.API.WeatherAPI
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
            .client(okHTTPClient.addInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .baseUrl(BASE_URL)
            .build()

        val weatherAPI = retrofit.create(WeatherAPI::class.java)
    }

    suspend fun fetchCurrentWeather(location: String) {

        Log.i("Repostory", "Vai chamar a api dentro do repository")

        val response = weatherAPI.fetchCurrentWeather(location, API_KEY)
        if (response.isSuccessful) {
            Log.i("Response", "${response.body()}")
        } else
            Log.i("Response erro", "${response.errorBody()}")
    }

}