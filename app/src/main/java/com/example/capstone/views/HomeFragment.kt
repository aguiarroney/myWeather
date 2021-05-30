package com.example.capstone.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.databinding.FragmentHomeBinding
import com.example.capstone.repository.Repository
import com.google.android.material.snackbar.Snackbar


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var _viewModel: HomeFragmentViewModel
    private lateinit var _binding: FragmentHomeBinding
    private lateinit var snackbar: Snackbar

    companion object {
        const val RESQUEST_CODE = 1
    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val repository = Repository()
        val factory = HomeFragmentViewModelFactory(repository)

        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)

        snackbar = Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            "This app only works with locations permissions granted",
            Snackbar.LENGTH_INDEFINITE
        )

        checkAndRequestLocationPermissions()

        _viewModel = ViewModelProvider(this, factory).get(HomeFragmentViewModel::class.java)

        _viewModel.getCurrentLocation(requireActivity())

        _viewModel.currentWeather.observe(viewLifecycleOwner, Observer {
            _binding.tvCurrentTemperature.text = it
        })

        _viewModel.currentCityName.observe(viewLifecycleOwner, Observer {
            _binding.tvCurrentLocation.text = it
            _binding.ivLocationIcon.setBackgroundResource(R.drawable.ic_location_icon)
        })

        _viewModel.currentLocation.observe(viewLifecycleOwner, Observer {
            _viewModel.fetchCurrentWeather()
        })

        return _binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        snackbar.dismiss()
    }

    private fun checkAndRequestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                RESQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.i("Permissao", "chamou callback")
        when (requestCode) {
            RESQUEST_CODE -> {
                if (grantResults.size > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Log.i("Permissao", "garantida")
                    _viewModel.getCurrentLocation(requireActivity())
                } else {
                    snackbar.setAction("Allow") {
                        checkAndRequestLocationPermissions()
                    }.show()
                }
            }
        }
    }


}