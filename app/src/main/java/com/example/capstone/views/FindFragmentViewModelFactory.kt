package com.example.capstone.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.repository.Repository
import com.example.capstone.repository.RoomRepository

class FindFragmentViewModelFactory(private val repository: RoomRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FindFragmentViewModel(repository) as T
    }

}