package com.example.capstone.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.databinding.FragmentHomeBinding
import com.example.capstone.repository.Repository


class HomeFragment : Fragment() {

    private lateinit var _viewModel: HomeFragmentViewModel
    private lateinit var _binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val repository = Repository()
        val factory = HomeFragmentViewModelFactory(repository)

        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)

        _viewModel = ViewModelProvider(this, factory).get(HomeFragmentViewModel::class.java)
        _viewModel.fetchCurrentWeather()

        _viewModel.currentWeather.observe(viewLifecycleOwner, Observer {
            _binding.tvCurrentTemperature.text = it
        })

        return _binding.root
    }

}