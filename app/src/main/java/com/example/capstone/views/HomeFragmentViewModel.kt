package com.example.capstone.views

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.repository.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch
import java.util.*

class HomeFragmentViewModel(private val repository: Repository) : ViewModel() {

    private var _currentWeather = MutableLiveData<String>()
    val currentWeather: LiveData<String> = _currentWeather

    private var _currentLocation = MutableLiveData<LatLng>()
    val currentLocation: LiveData<LatLng> = _currentLocation

    private var _currentCityName = MutableLiveData<String>()
    val currentCityName: LiveData<String> = _currentCityName

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient


    fun fetchCurrentWeather() {

        viewModelScope.launch {
            Log.i("ViewModel", "Vai chamar a api")
            _currentWeather.value = repository.fetchCurrentWeather("Rio de Janeiro")
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(activity: Activity) {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {

            val geocoder = Geocoder(activity.applicationContext, Locale.getDefault())
            val addres = geocoder.getFromLocation(it.latitude, it.longitude, 1)

            Log.i(
                "Address",
                "${addres[0].subAdminArea} : ${addres[0].adminArea} : ${addres[0].locality}"
            )
            _currentCityName.value = addres[0].subAdminArea + ", " + addres[0].adminArea
            _currentLocation.value = LatLng(it.latitude, it.longitude)
        }
    }


}