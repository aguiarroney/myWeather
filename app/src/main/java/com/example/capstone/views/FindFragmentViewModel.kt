package com.example.capstone.views

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.capstone.repository.Repository

class FindFragmentViewModel(private val repository: Repository) : ViewModel() {


    fun validateFields() {
        Log.i("view model", "botão ok")
    }

}