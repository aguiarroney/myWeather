package com.example.capstone.views

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.repository.Repository
import kotlinx.coroutines.launch

class HomeFragmentViewModel(private val repository: Repository): ViewModel() {

    fun fetchCurrentWeather(){

        viewModelScope.launch {
            Log.i("ViewModel", "Vai chamar a api")
            repository.fetchCurrentWeather("Rio de Janeiro")
        }
    }

}