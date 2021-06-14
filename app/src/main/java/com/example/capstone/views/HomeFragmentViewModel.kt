package com.example.capstone.views

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.model.WeatherModel
import com.example.capstone.repository.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.util.*

class HomeFragmentViewModel(private val repository: Repository) : ViewModel() {

    private var _currentWeather = MutableLiveData<WeatherModel>()
    val currentWeather: LiveData<WeatherModel> = _currentWeather

    private var _currentLocation = MutableLiveData<LatLng>()
    val currentLocation: LiveData<LatLng> = _currentLocation

    private var _currentCityName = MutableLiveData<String>()
    val currentCityName: LiveData<String> = _currentCityName

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    fun fetchCurrentWeather() {

        viewModelScope.launch {
            Log.i("ViewModel", "Vai chamar a api")

            val response = repository.fetchCurrentWeather(
                _currentLocation.value!!.latitude,
                _currentLocation.value!!.longitude
            )

            if (response != null) {
                if (response.isSuccessful) {
                    _currentWeather.value = response.body()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(activity: Activity) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {

            Log.i("geocoder", "$it")

            it?.let {
                val geocoder = Geocoder(activity.applicationContext, Locale.getDefault())
                val addres = geocoder.getFromLocation(it.latitude, it.longitude, 1)

                Log.i(
                    "Address",
                    "${addres[0].subAdminArea} : ${addres[0].adminArea} : ${addres[0].locality}"
                )

                if (addres[0].locality.isNullOrEmpty()) {
                    _currentCityName.value = addres[0].subAdminArea + ", " + addres[0].adminArea
                } else {
                    _currentCityName.value = addres[0].locality + ", " + addres[0].adminArea
                }

                _currentLocation.value = LatLng(it.latitude, it.longitude)
            }
        }
    }

    fun formatDateTime(timeSinceEpoch: Long): String {
        val sdf = java.text.SimpleDateFormat("HH:mm:ss")
        val dateSunrise = java.util.Date(timeSinceEpoch * 1000)
        return sdf.format(dateSunrise)
    }

}