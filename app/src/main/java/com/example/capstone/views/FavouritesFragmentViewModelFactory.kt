package com.example.capstone.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.repository.RoomRepository

class FavouritesFragmentViewModelFactory(private val repository: RoomRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FavouriteFragmentViewModel(repository) as T
    }
}