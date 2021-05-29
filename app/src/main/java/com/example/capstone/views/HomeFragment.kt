package com.example.capstone.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.repository.Repository


class HomeFragment : Fragment() {

    private lateinit var _viewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val repository = Repository()
        val factory = HomeFragmentViewModelFactory(repository)

        _viewModel = ViewModelProvider(this, factory).get(HomeFragmentViewModel::class.java)
        _viewModel.fetchCurrentWeather()

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

}