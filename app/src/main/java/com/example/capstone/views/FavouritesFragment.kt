package com.example.capstone.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.capstone.R
import com.example.capstone.databinding.FavouriteItemBinding
import com.example.capstone.databinding.FragmentFavouritesBinding
import com.example.capstone.local.getDatabase
import com.example.capstone.repository.RoomRepository


class FavouritesFragment : Fragment() {

    private lateinit var _binding: FragmentFavouritesBinding
    private lateinit var _viewModel: FavouriteFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_favourites, container, false)

        val database = getDatabase(requireContext())
        val repository = RoomRepository(database)
        val factory = FavouritesFragmentViewModelFactory(repository)

        _viewModel = ViewModelProvider(this, factory).get(FavouriteFragmentViewModel::class.java)

        _viewModel.favouriteLocalWeathers.observe(viewLifecycleOwner, Observer { locations ->
            locations?.let {
                Log.i("favourites", "recebeu do db")

                locations.forEach {
                    val listItem: FavouriteItemBinding = DataBindingUtil.inflate(
                        layoutInflater,
                        R.layout.favourite_item,
                        container,
                        false
                    )
                    listItem.localWeather = it
                    _binding.localWeatherList.addView(listItem.root)
                }

                if(it.isNotEmpty())
                    _binding.tbnDeleteFavourites.isVisible = true
            }
        })

        _binding.tbnDeleteFavourites.setOnClickListener {
            _viewModel.deleteAllFromDatabase()
            _binding.localWeatherList.removeAllViews()
            _binding.tbnDeleteFavourites.isVisible = false
        }

        return _binding.root
    }
}