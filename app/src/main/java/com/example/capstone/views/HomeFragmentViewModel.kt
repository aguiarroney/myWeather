package com.example.capstone.views

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.repository.Repository
import kotlinx.coroutines.launch

class HomeFragmentViewModel(private val repository: Repository): ViewModel() {

    private var _currentWeather = MutableLiveData<String>()
    val currentWeather: LiveData<String> = _currentWeather

    private var _currentLocation = MutableLiveData<String>()
    val currentLocation: LiveData<String> = _currentLocation


    fun fetchCurrentWeather(){

        viewModelScope.launch {
            Log.i("ViewModel", "Vai chamar a api")
            _currentWeather.value = repository.fetchCurrentWeather("Rio de Janeiro")
        }
    }

}