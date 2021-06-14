package com.example.capstone.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.model.WeatherModel
import com.example.capstone.repository.RoomRepository
import com.example.capstone.util.asDataBaseModel
import kotlinx.coroutines.launch

class FindFragmentViewModel(private val repository: RoomRepository) : ViewModel() {

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
                    if(response.isSuccessful){
                        _myWeather.value = response.body()
                        myWeather = _myWeather
                    }
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

    fun saveFavourite() {
        _myWeather.value?.let {
            viewModelScope.launch {
                repository.insertLocationWeather(it.asDataBaseModel())
                Log.i("Vai salvar", "sera")
            }
        }
    }

    fun getFavouritesFromDb() {
        viewModelScope.launch {
            val response = repository.getAllWeather()
            Log.i("pegou do db", "$response")
        }
    }
}