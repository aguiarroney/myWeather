package com.example.capstone.views

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.model.WeatherModel
import com.example.capstone.repository.Repository
import kotlinx.coroutines.launch

class FindFragmentViewModel(private val repository: Repository) : ViewModel() {

    var myCity: String? = null
    var myState: String? = null
    var myCountry: String? = null

    private var _fields = MutableLiveData<Boolean>()
    val fields: LiveData<Boolean> = _fields

    private var _nullResponseFromNetwork = MutableLiveData<Boolean>()
    val nullResponseFromNetwork: LiveData<Boolean> = _nullResponseFromNetwork

    private var _myWeather = MutableLiveData<WeatherModel>()
    var myWeather: LiveData<WeatherModel> = _myWeather

    fun validateFields() {

        if (myCity.isNullOrEmpty() || myState.isNullOrEmpty() || myCountry.isNullOrEmpty()) {
            _fields.value = true
        } else {

            viewModelScope.launch {
                val response =
                    repository.fetchWeatherByLocationName(myCity!!, myState!!, myCountry!!)

                if (response != null) {
                    _myWeather.value = response
                    myWeather = _myWeather
                } else {
                    _nullResponseFromNetwork.value = true
                }
            }
        }
    }

    fun missingFields() {
        _fields.value = false
    }

    fun cityNotFound() {
        _nullResponseFromNetwork.value = false
    }
}