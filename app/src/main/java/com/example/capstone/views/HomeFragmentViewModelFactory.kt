package com.example.capstone.views

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.repository.Repository

class HomeFragmentViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T: ViewModel?> create(modelClass: Class<T>): T{
        return HomeFragmentViewModel(repository) as T
    }
}