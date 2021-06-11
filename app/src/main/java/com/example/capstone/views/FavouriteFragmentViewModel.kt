package com.example.capstone.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.capstone.local.LocationDatabaseEntities
import com.example.capstone.repository.RoomRepository
import kotlinx.coroutines.launch

class FavouriteFragmentViewModel(private val repository: RoomRepository) : ViewModel() {

    private val _favouriteLocalWeathers = MutableLiveData<List<LocationDatabaseEntities>>()
    var favouriteLocalWeathers = _favouriteLocalWeathers

    init {
        viewModelScope.launch {
            _favouriteLocalWeathers.value = repository.getAllWeather()
        }
    }

}