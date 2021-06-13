package com.example.capstone.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.databinding.FragmentHomeBinding
import com.example.capstone.repository.Repository
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso


@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private lateinit var _viewModel: HomeFragmentViewModel
    private lateinit var _binding: FragmentHomeBinding
    private lateinit var snackbar: Snackbar

    companion object {
        const val RESQUEST_CODE = 1
        const val ICON_URL = "https://openweathermap.org/img/wn/"
        const val EXTENSION_ICON_URL = "@2x.png"
    }

    @RequiresApi(Build.VERSION_CODES.O)
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

        _viewModel.currentWeather.observe(viewLifecycleOwner, {
            it?.let {

                Log.i("clima", "$it")

                _binding.tvCurrentTemperature.text =
                    getString(R.string.text_current_temperature, it.main.temp.toInt())

                _binding.tvWeatherDescription.text = it.weather[0].description

                Picasso.with(context).load("${ICON_URL}${it.weather[0].icon}${EXTENSION_ICON_URL}")
                    .into(_binding.ivWeatherIcon)

                _binding.tvFeelsLike.text =
                    getString(R.string.text_feels_like_temperature, it.main.feels_like.toInt())

                _binding.tvMaxMin.text =
                    getString(
                        R.string.text_max_min_temperature,
                        it.main.temp_min.toInt(),
                        it.main.temp_max.toInt()
                    )

                _binding.tvSunriseValue.text =
                    _viewModel.formatDateTime(it.sys.sunrise.toLong())

                _binding.tvSunsetValue.text = _viewModel.formatDateTime(it.sys.sunset.toLong())

                _binding.tvWindValue.text =
                    getString(R.string.text_wind_speed, it.wind.speed.toString().format(2))
                _binding.tvHumidityValue.text = getString(R.string.text_humidity, it.main.humidity)
            }
        })

        _viewModel.currentCityName.observe(viewLifecycleOwner, {
            it?.let {
                _binding.tvCurrentLocation.text = it
                _binding.ivLocationIcon.setBackgroundResource(R.drawable.ic_location_icon)
            }
        })

        _viewModel.currentLocation.observe(viewLifecycleOwner, {
            it?.let {
                _viewModel.fetchCurrentWeather()
            }
        })

        return _binding.root
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        snackbar.dismiss()
//    }

    private fun checkAndRequestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
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
                if (grantResults.isNotEmpty() && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
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