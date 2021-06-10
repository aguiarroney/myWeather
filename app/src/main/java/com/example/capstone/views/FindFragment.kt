package com.example.capstone.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.databinding.FragmentFindBinding
import com.example.capstone.repository.Repository
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class FindFragment : Fragment() {

    private lateinit var _binding: FragmentFindBinding
    private lateinit var _viewModel: FindFragmentViewModel
    private lateinit var _snackbar: Snackbar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_find, container, false)

        val repository = Repository()
        val factory = FindFragmentViewModelFactory(repository)

        _viewModel = ViewModelProvider(this, factory).get(FindFragmentViewModel::class.java)

        _binding.findViewModel = _viewModel

        _viewModel.fields.observe(viewLifecycleOwner, Observer {
            if (it) {
                _snackbar = Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "Missing Fields",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Ok") {
                    _viewModel.missingFields()
                }
                _snackbar.show()
            }
        })

        _binding.btnFind.setOnClickListener {
            _binding.etFindState.hideKeyboard()
            _viewModel.validateFields()
        }

        _viewModel.myWeather.observe(viewLifecycleOwner, Observer {
            clearTextFields()

            _binding.cvMainCard.isVisible = true
            _binding.tvCurrentLocationFind.text = it.name
            _binding.ivLocationIconFind.setBackgroundResource(R.drawable.ic_location_icon)

            _binding.tvCurrentTemperatureFind.text =
                getString(R.string.text_current_temperature, it.main.temp.toInt())
            _binding.tvWeatherDescriptionFind.text = it.weather[0].description

            Picasso.with(context)
                .load("${HomeFragment.ICON_URL}${it.weather[0].icon}${HomeFragment.EXTENSION_ICON_URL}")
                .into(_binding.ivWeatherIconFind)

            _binding.tvFeelsLikeFind.text =
                getString(R.string.text_feels_like_temperature, it.main.feels_like.toInt())

            _binding.tvMaxMinFind.text =
                getString(
                    R.string.text_max_min_temperature,
                    it.main.temp_min.toInt(),
                    it.main.temp_max.toInt()
                )
        })

        _viewModel.nullResponseFromNetwork.observe(viewLifecycleOwner, Observer {
            if (it) {
                _snackbar = Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "City Not Found",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Ok") {
                    _viewModel.cityNotFound()
                    clearTextFields()

                }
                _snackbar.show()
            }
        })

        return _binding.root
    }

    private fun clearTextFields() {
        _binding.etFindState.text.clear()
        _binding.etFindCity.text.clear()
        _binding.etFindCountry.text.clear()
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}